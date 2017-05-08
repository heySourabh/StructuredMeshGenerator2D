# StructuredMeshGenerator2D
A structured mesh generator for creating 2D mesh using Transfinite Interpolation (TFI) method.

Below is an example of multi-block mesh generated using multiple geometry defining files:

![Sample output](https://github.com/heySourabh/StructuredMeshGenerator2D/blob/master/demo/multi_block_mesh_example.png)

Below is another example of multi-block mesh generated using multiple quad blocks:

![Sample output](https://github.com/heySourabh/StructuredMeshGenerator2D/blob/master/demo/sample_output_3.png)

and here is a solution produced by importing the above multi-block (unstructured) mesh in a CFD solver, which uses the boundary markers applied in this meshing code:

![Sample output](https://github.com/heySourabh/StructuredMeshGenerator2D/blob/master/demo/forward_facing_step.png)

Below is a sample mesh generated, where the geometry is created using some helper 
methods available within this project.

![Sample output](https://github.com/heySourabh/StructuredMeshGenerator2D/blob/master/demo/sample_output.png)

and here is the mesh being used to solve convergent-divergent nozzle problem in a CFD solver.

![Sample output](https://github.com/heySourabh/StructuredMeshGenerator2D/blob/master/demo/convergent_divergent_nozzle.png)

Here is a mesh generated using geometry read from a file:

![Sample output](https://github.com/heySourabh/StructuredMeshGenerator2D/blob/master/demo/sample_output_1.png)

and here is a CFD solution for flow over a cylinder using the above mesh:

![Sample output](https://github.com/heySourabh/StructuredMeshGenerator2D/blob/master/demo/flow_over_cylinder.png)

Here is another mesh generated using geometry from file:

![Sample output](https://github.com/heySourabh/StructuredMeshGenerator2D/blob/master/demo/sample_output_2.png)

and here is a CFD solution for compressible flow using the above mesh:

![Sample output](https://github.com/heySourabh/StructuredMeshGenerator2D/blob/master/demo/oblique_shock.png)


