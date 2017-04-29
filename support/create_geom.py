import numpy as np

numPoints = 20
circRadius = 1.0
cx = 0.0
cy = 0.0
angles = np.linspace(180 * np.pi / 180, 0, numPoints)
for a in angles:
    x = circRadius * np.cos(a) + cx
    y = circRadius * np.sin(a) + cy
    print("%-16f %-16f"%(x,y))