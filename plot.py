import numpy as np
import matplotlib.pyplot as plt

meshFile = open("mesh.dat")
numXi = int(meshFile.readline().split("=")[1])
numEta = int(meshFile.readline().split("=")[1])

x, y = np.loadtxt("mesh.dat", skiprows=3, unpack=True)
x = np.reshape(x, (numXi, numEta))
y = np.reshape(y, (numXi, numEta))
plt.clf()
# plot internal lines
for xi in range(numXi):
    if xi == 0: label="xi=constant"
    else: label = None
    plt.plot(x[xi], y[xi], "-b", label=label)
for eta in range(numEta):
    if eta == 0: label="eta=constant"
    else: label = None
    plt.plot(x[:,eta], y[:,eta], "-r", label=label)

plt.legend(loc="best")