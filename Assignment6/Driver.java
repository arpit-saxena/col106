import java.util.ArrayList;

import java.util.Arrays; 

import java.io.*;
class Driver {


  public static void main(String[] args) {
	try{
       	    BufferedReader br = null;
            br = new BufferedReader(new FileReader(args[0]));
	    ShapeInterface shape_intr = new Shape();
            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
		//System.out.println("cmd is "+ Arrays.toString(cmd));	

                if (cmd.length == 0) {
                    System.err.println("Error parsing:1 ");
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

		ArrayList<Float> inp = new ArrayList<>();
		 int firstskip=0;
		for (String val:cmd) {

			if(0==firstskip){
			firstskip++;
			continue;
			}

			inp.add(Float.parseFloat(val.trim()));
			System.out.print(val + " ");
		}
		
		//System.out.println("arguments " +Arrays.toString(input.toArray()));


		float[] input = new float[inp.size()]; 
		int k = 0;

		for (Float f : inp) {
		    input[k++] = f; 
		}
                switch (cmd[0]) {
			

		

                    case "ADD_TRIANGLE":
			System.out.print("Add TriangleInterface: ");	
			
			if (shape_intr.ADD_TRIANGLE(input)) {
				System.out.println("True");
			} else {
				System.out.println("False");
			}
                        break;

                    case "TYPE_MESH":
			int mesh_type = shape_intr.TYPE_MESH();
			System.out.println("Mesh type " + mesh_type);
                        break;
                    case "COUNT_CONNECTED_COMPONENTS":
			int count_connected = shape_intr.COUNT_CONNECTED_COMPONENTS();
			System.out.println("Number of connected components = "+ count_connected);
                        break;
                    case "BOUNDARY_EDGES":		
			System.out.print("Getting boundary edges: ");	

			 EdgeInterface [] boundary_edges= shape_intr.BOUNDARY_EDGES();
			 if (boundary_edges == null) {
				 System.out.println("Null");
			 } else {
				print(boundary_edges);
				System.out.println();
			}
                        break;
                    case "IS_CONNECTED":
			System.out.print("CHECKING IS_CONNECTED: ");
			float [] triangle1 = new float[9]; 
			float [] triangle2 = new float[9]; 
			for (int i =0;i<9;i++)
			{
				triangle1[i]=input[i];
			}
			for (int i =9;i<18;i++)
			{
				triangle2[i-9]=input[i];
			}
				


			boolean is_con = shape_intr.IS_CONNECTED(triangle1, triangle2);		
			System.out.println("Is connected = " + is_con);
                        break;

                    case "NEIGHBORS_OF_POINT":
			System.out.print("FINDING NEIGHBORS_OF_POINT: ");
			 print(shape_intr.NEIGHBORS_OF_POINT(input));
			 System.out.println();
			break;

                    case "NEIGHBORS_OF_TRIANGLE":
			System.out.print("FINDING NEIGHBORS_OF_TRIANGLE: " );
			print(shape_intr.NEIGHBORS_OF_TRIANGLE(input));
			System.out.println();
			break;

                    case "INCIDENT_TRIANGLES":
			System.out.print("FINDING INCIDENT_TRIANGLES: " );
			print(shape_intr.INCIDENT_TRIANGLES(input));
			System.out.println();
			break;

                    case "VERTEX_NEIGHBOR_TRIANGLE":
			System.out.print("FINDING VERTEX_NEIGHBOR_TRIANGLE: " );
			print(shape_intr.VERTEX_NEIGHBOR_TRIANGLE(input));
			System.out.println();
                       	 break;

                    case "EXTENDED_NEIGHBOR_TRIANGLE":
			System.out.print(" FINDING EXTENDED_NEIGHBOR_TRIANGLE:  " );
			print(shape_intr.EXTENDED_NEIGHBOR_TRIANGLE(input));
			System.out.println();
			break;

	          case "MAXIMUM_DIAMETER":
			System.out.print(" Finding diameter: " );
			int diameter = shape_intr.MAXIMUM_DIAMETER();
			System.out.println(diameter);

                        break;
                    case "EDGE_NEIGHBOR_TRIANGLE":
			System.out.print(" Finding EDGE_NEIGHBOR_TRIANGLEL ");
			 EdgeInterface [] edge_neighbors_of_triangle = shape_intr.EDGE_NEIGHBOR_TRIANGLE(input);
			 print(edge_neighbors_of_triangle);
			 System.out.println();
                        break;

                   case "FACE_NEIGHBORS_OF_POINT":
			System.out.print(" Finding FACE_NEIGHBORS_OF_POINT: ");
			 TriangleInterface [] face_nbrs = shape_intr.FACE_NEIGHBORS_OF_POINT(input);
			 print(face_nbrs);
			 System.out.println();			 
                        break;



                   case "EDGE_NEIGHBORS_OF_POINT":
			System.out.print(" Finding EDGE_NEIGHBORS_OF_POINT: ");
			 EdgeInterface [] edge_nbrs = shape_intr.EDGE_NEIGHBORS_OF_POINT(input);
			 print(edge_nbrs);
			 System.out.println();
                        break;

                    case "TRIANGLE_NEIGHBOR_OF_EDGE":
			System.out.print(" Finding TRIANGLE_NEIGHBOR_OF_EDGE: ");
			 TriangleInterface [] triangle_neighbors = shape_intr.TRIANGLE_NEIGHBOR_OF_EDGE(input);
			 print(triangle_neighbors);
			 System.out.println();
			 break;
		

	          case "CENTROID":
			System.out.print(" Finding Centroid: " );
			PointInterface [] centroid_array = shape_intr.CENTROID();
			print(centroid_array);
			System.out.println();

                        break;
                    case "CENTROID_OF_COMPONENT":
			System.out.print(" Finding CENTROID_OF_COMPONENT: ");
			 PointInterface centroid_of_component = shape_intr.CENTROID_OF_COMPONENT(input);
			 print(centroid_of_component);
			 System.out.println();
			 break;

                    case "CLOSEST_COMPONENTS":
			System.out.print(" Finding CLOSEST_COMPONENTS: ");
			  PointInterface [] closest_vertices = shape_intr.CLOSEST_COMPONENTS();
			  print(closest_vertices);
			  System.out.println();
			break;
		


	//	default :System.out.println(cmd[0] +" not found");	
	//		break;
			
                }

            }
	}
	
	catch(Exception e)
	{
		System.err.println("Error parsing: 2	 " +e);
		e.printStackTrace();
	}
	

}

	static void print(PointInterface p) {
		float[] c = p.getXYZcoordinate();
		System.out.print("(" + c[0] + "," + c[1] + "," + c[2] + ")");
	}

	static void print(PointInterface[] p) {
		System.out.print("[");
		for (PointInterface pp : p) {
			print(pp);
			System.out.print(", ");
		}
		System.out.print("]");
	}

	static void print(EdgeInterface e) {
		print(e.edgeEndPoints());
	}

	static void print(EdgeInterface[] e) {
		System.out.print("[");
		for (EdgeInterface ee : e) {
			print(ee);
			System.out.print(", ");
		}
		System.out.print("]");
	}

	static void print(TriangleInterface t) {
		print(t.triangle_coord());
	}

	static void print(TriangleInterface[] t) {
		for (TriangleInterface tt : t) 
			print(tt.triangle_coord());
	}
}
