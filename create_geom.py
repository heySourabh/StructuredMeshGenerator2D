import numpy as np

numPoints = 20
circRadius = 5.0
cx = 0.0
cy = 0.0
angles = np.linspace(0, 180 * np.pi / 180, numPoints)
for a in angles:
    x = circRadius * np.cos(a) + cx
    y = circRadius * np.sin(a) + cy
    print("%-16f %-16f"%(x,y))