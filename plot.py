import numpy as np
import matplotlib.pyplot as plt

meshFile = open("mesh.dat")
numXi = int(meshFile.readline().split("=")[1])
numEta = int(meshFile.readline().split("=")[1])

x, y = np.loadtxt("mesh.dat", skiprows=3, unpack=True)
x = np.reshape(x, (numXi, numEta))
y = np.reshape(y, (numXi, numEta))

for xi in range(numXi):
    plt.plot(x[xi], y[xi], "-b")
for eta in range(numEta):
    plt.plot(x[:,eta], y[:,eta], "-r")