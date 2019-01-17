
# Plotting and data reading functions for Feedforward Neural Networks.

from tensorflow.examples.tutorials.mnist import input_data
import matplotlib.pyplot as plt
import numpy as np

plt.close("all")

fig, axarr = plt.subplots(3,figsize=(10, 15))
fig.show()

nx,ny = 5, 5    
fig_p, axarr_p = plt.subplots(ny,nx,figsize=(15,15))
fig_p.show()

def display_predictions(network,show_pct=False):
    fashion = ["T-shirt","Trouser","Pullover","Dress",
               "Coat","Sandal","Shirt","Sneaker","Bag","Boot"]
    permutation = np.random.permutation(len(network.testX))
    for y in range(ny):
        for x in range(nx):
            i = permutation[x*ny+y]
            predicted = network.predict(network.testX[i])
            correct   = np.argmax(network.testY[i])
            if predicted == correct:
                color = "Greys"
            else:
                color = "Reds"
            label=fashion[predicted]
            if show_pct:
                label += " (" + '{0:.2f}'.format(network.predict_pct(predicted)) + ")"
            axarr_p[y,x].set_xlabel(label,fontsize=20)
            axarr_p[y,x].imshow(network.testX[i].reshape(28,28),cmap=color)
            axarr_p[y,x].tick_params(
                axis='x',          # changes apply to the x-axis
                which='both',      # both major and minor ticks are affected
                bottom='off',      # ticks along the bottom edge are off
                top='off',         # ticks along the top edge are off
                labelbottom='off') # labels along the bottom edge are off
            axarr_p[y,x].axes.get_yaxis().set_visible(False)
    fig_p.canvas.draw()    


def plot_stats(epsilon, a, loss, test_acc_log, train_acc_log):

    L = len(a)

    # Plot accuracies
    axarr[0].clear()
    axarr[0].set_title("Classification accuracy",fontsize=18)
    axarr[0].plot(test_acc_log)
    axarr[0].plot(train_acc_log)
    axarr[0].legend(["Test","Training"])

    # Plot activations
    axarr[1].clear()
    axarr[1].set_title("Activations",fontsize=18)
    axarr[1].violinplot(a)

    # Plot logs
    axarr[2].clear()
    axarr[2].set_title("Loss (epsilon="+str(epsilon)+")",fontsize=18)
    axarr[2].plot(loss)

    fig.canvas.draw()

def read_data():
    mnist = input_data.read_data_sets('fashion', one_hot=True)
    return mnist.train.images, mnist.train.labels, mnist.test.images, mnist.test.labels


