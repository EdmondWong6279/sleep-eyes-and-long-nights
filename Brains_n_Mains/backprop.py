#
# Backpropagation
#
# Per Kristian Lehre
# University of Birmingham
# November 2017
#
# Based on 'Neural Networks and Deep Learning' by Michael Nielsen.
# http://neuralnetworksanddeeplearning.com/index.html
#
# Fashion MNIST data set from
# https://github.com/zalandoresearch/fashion-mnist
#
#
# This example uses Mean Squared Error as cost function. For classification
# problems, we will see in later lectures that it is more appropriate to use
# a softmax layer. 
# 

import sys
import numpy as np
import time
import fnn_utils

# Activation functions
def sigmoid(x):
    return 1 / (1+np.exp(-x))
def sigmoid_d(x):
    return sigmoid(x)*(1-sigmoid(x))
def relu(x):
    return x * (x > 0.0)
def relu_d(x):
    return 1.0 * (x > 0)
       
class BackPropagation:
    
    def __init__(self,network_shape=[784,10,10]):

        # Read the training and test data
        self.trainX, self.trainY, self.testX, self.testY = fnn_utils.read_data()

        # Number of layers in the network
        self.L = len(network_shape)

        self.crossings = [(1 if i < 1 else network_shape[i-1],network_shape[i])
                          for i in range(self.L)]

        # Create the network
        self.a             = [np.zeros(m) for m in network_shape]
        self.db            = [np.zeros(m) for m in network_shape]
        self.b             = [np.random.normal(0,1/10,m) for m in network_shape]
        self.z             = [np.zeros(m) for m in network_shape]
        self.delta         = [np.zeros(m) for m in network_shape]
        self.w             = [np.random.uniform(-1/np.sqrt(m0),1/np.sqrt(m0),(m1,m0))
                               for (m0,m1) in self.crossings]
        self.dw            = [np.zeros((m1,m0)) for (m0,m1) in self.crossings]
        self.nabla_C_out   = np.zeros(network_shape[-1])

        # Choose activation function
        self.phi           = relu
        self.phi_d         = relu_d
        
        # Store activations over the batch for plotting
        self.batch_a       = [np.zeros(m) for m in network_shape]
                
    def forward(self, x):
        """ Set first activation in input layer equal to the input vector x, 
            feed forward through layers, then return activation of last layer.
        """
        self.a[0] = x - 0.5      # Center the input values between [-0.5,0.5]
        for l in range(1,self.L):
            self.z[l] = np.dot(self.w[l], self.a[l-1]) + self.b[l]
            self.a[l] = self.phi(self.z[l])
        return(self.a[self.L-1])

    def backward(self,x, y):
        """ Compute local gradients, then return gradients of network.
        """
        self.nabla_C_out = self.a[self.L-1] - y
        self.delta[self.L-1]  = self.nabla_C_out * self.phi_d(self.z[self.L-1])
        for l in range(self.L-2,0,-1):
            self.delta[l] = np.dot(self.w[l+1].T, self.delta[l+1]) * self.phi_d(self.z[l])
    
    def predict(self, x):
        return np.argmax(self.forward(x))
    
    def evaluate(self, X, Y, N):
        """ Evaluate the network on a random subset of size N. """
        num_data = min(len(X),len(Y))
        samples = np.random.randint(num_data,size=N)
        results = [(self.predict(x), np.argmax(y))
                    for (x,y) in zip(X[samples],Y[samples])]
        return sum(int(x==y) for (x,y) in results)/N

    def loss(self, pred, y):
        return np.sum(np.square(pred-y))
    
    def sgd(self,
            batch_size=50,
            epsilon=0.001,
            epochs=1000):

        """ Mini-batch gradient descent on training data.

            batch_size: number of training examples between each weight update
            epsilon:    learning rate
            epochs:     the number of times to go through the entire training data
        """
        
        # Compute the number of training examples and number of mini-batches.
        N = min(len(self.trainX), len(self.trainY))
        num_batches = int(N/batch_size)

        # Variables to keep track of statistics
        loss_log      = []
        test_acc_log  = []
        train_acc_log = []

        timestamp = time.time()
        timestamp2 = time.time()
        
        # In each "epoch", the network is exposed to the entire training set.
        for t in range(epochs):

            # We will order the training data using a random permutation.
            permutation = np.random.permutation(N)
            
            # Evaluate the accuracy on 1000 samples from the training and test data
            test_acc_log.append( self.evaluate(self.testX, self.testY, 1000))
            train_acc_log.append( self.evaluate(self.trainX, self.trainY, 1000))
            batch_loss = 0

            for k in range(num_batches):
                
                # Reset buffer containing updates
                for l in range(self.L):
                    self.dw[l].fill(0.0)
                    self.db[l].fill(0.0)

                # Mini-batch loop
                for i in range(batch_size):

                    # Select the next training example (x,y)
                    x = self.trainX[permutation[k*batch_size+i]]
                    y = self.trainY[permutation[k*batch_size+i]]

                    # Feed forward inputs
                    self.forward(x)

                    # Compute gradients
                    self.backward(x,y)

                    # Add gradients from the training example to deltas of weights and biases
                    for l in range(1,self.L):
                        self.dw[l] = self.dw[l] + np.outer(self.delta[l],self.a[l-1])
                        self.db[l] = self.db[l] + self.delta[l]

                    # Update loss log
                    batch_loss += self.loss(self.a[self.L-1], y)

                    for l in range(self.L):
                        self.batch_a[l] += self.a[l] / batch_size
                                    
                # Update the weights at the end of the mini-batch
                for l in range(1,self.L):
                    self.w[l] = self.w[l] - (epsilon / batch_size) * self.dw[l]
                    self.b[l] = self.b[l] - (epsilon / batch_size) * self.db[l]
                
                # Update logs
                loss_log.append( batch_loss / batch_size )
                batch_loss = 0

                # Update plot of statistics every 10 seconds.
                if time.time() - timestamp > 10:
                    timestamp = time.time()
                    fnn_utils.plot_stats(epsilon,
                                         self.batch_a,
                                         loss_log,
                                         test_acc_log,
                                         train_acc_log)

                # Display predictions every minute.
                if time.time() - timestamp2 > 60:
                    timestamp2 = time.time()
                    fnn_utils.display_predictions(self)

                # Reset batch average
                for l in range(self.L):
                    self.batch_a[l].fill(0.0)

def main():
    bp = BackPropagation()
    if(len(sys.argv)==2):
        epsilon = float(sys.argv[1])
        print("Learning rate :" + str(epsilon))
        bp.sgd(epsilon=epsilon)
    else:
        bp.sgd()

if __name__ == "__main__":
    main()
    

