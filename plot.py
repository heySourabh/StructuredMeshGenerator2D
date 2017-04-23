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
    if xi == 0: label="$\\xi$=constant"
    else: label = None
    plt.plot(x[xi], y[xi], "-", color=(0, 0, 1, 0.5), label=label)
for eta in range(numEta):
    if eta == 0: label="$\\eta$=constant"
    else: label = None
    plt.plot(x[:,eta], y[:,eta], "-", color=(1, 0, 0, 0.5), label=label)

plt.plot(x[:,0], y[:,0], "-r", lw=1.5)
plt.plot(x[:,-1], y[:,-1], "-r", lw=1.5)

plt.plot(x[0], y[0], "-b", label=label)
plt.plot(x[-1], y[-1], "-b", label=label)

plt.legend(loc="best")
plt.axes().set_aspect("equal")

