import numpy as np

numPoints = 10
circRadius = 2.0
cx = 0.0
cy = 0.0
angles = np.linspace(0 * np.pi / 180, 45 * np.pi / 180, numPoints)
for a in angles:
    x = circRadius * np.cos(a) + cx
    y = circRadius * np.sin(a) + cy
    print("%-20.15f %-20.15f"%(x,y))