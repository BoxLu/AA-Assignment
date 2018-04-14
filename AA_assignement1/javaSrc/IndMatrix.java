import java.io.*;
import java.util.*;


/**
 * Incidence matrix implementation for the FriendshipGraph interface.
 * <p>
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class IndMatrix<T extends Object> implements FriendshipGraph<T> {

	/*Compile:
	javac -cp .:jopt-simple-5.02.jar:sample.jar *.java
	
	Run:
	java -cp .:jopt-simple-5.02.jar:sample.jar GraphTester <implementation>
	python assign1TestScript.py -v javaSrc indmat tests/test1.in
	
	
	*/

    /**
     * Contructs empty graph.
     */
    private ArrayList<String> edges = new ArrayList<>();
    private ArrayList<T> vert = new ArrayList<>();
    private String[][] grapher;

    public IndMatrix() {
        // Implement me!
        grapher = new String[3][3];
        for (int i = 0; i < grapher.length; i++) {
            for (int j = 0; j < grapher.length; j++) {
                grapher[i][j] = "0";
            }
        }
        /*graph should be empty*/
    } // end of IndMatrix()


    public void addVertex(T vertLabel) {
        // Implement me!
		for (int i =0; i<vert.size();i++ ) {
			if (vert.get(i).equals(vertLabel)) {
				System.err.println("Vertex already exists");
				return;
			}
		}
        vert.add(vertLabel);
        addtoMatrix();
        setMatrix();
    } // end of addVertex()

    public void addtoMatrix() {
        grapher = new String[vert.size() + 1][edges.size() + 1];
        if (vert.size() > edges.size()) {
            for (int i = 0; i < vert.size(); i++) {
                grapher[i + 1][0] = vert.get(i).toString();
                if (i < edges.size()) {
                    grapher[0][i + 1] = edges.get(i);
                }
            }
        } else {
            for (int i = 0; i < edges.size(); i++) {
                grapher[0][i + 1] = edges.get(i);
                if (i < vert.size()) {
                    grapher[i + 1][0] = vert.get(i).toString();
                }
            }
        }
    }

    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
		boolean found1=false;
		boolean found2=false;
		String token[];
		if (edges.size() > vert.size()) {
			for (int i =0; i <edges.size() ; i++) {
				if (i < vert.size()) {
					if (vert.get(i).equals(srcLabel)) {
						found1 = true;
					}
					if (vert.get(i).equals(tarLabel)) {
						found2=true;
					}
				}
				token = edges.get(i).split(" ");
				if (token[0].equals(srcLabel.toString()) && token[1].equals(tarLabel.toString()) || token[1].equals(srcLabel.toString()) && token[0].equals(tarLabel.toString()) ) {
					System.err.println("Edge already exists");
					return;
				}
			}
		} else {
			for (int i =0; i <vert.size() ; i++) {
				if (vert.get(i).equals(srcLabel)) {
					found1 = true;
				}
				if (vert.get(i).equals(tarLabel)) {
					found2=true;
				}
				if (i < edges.size()) {
					token = grapher[0][i+1].split(" ");
					if (token[0].equals(srcLabel.toString()) && token[1].equals(tarLabel.toString()) || token[1].equals(srcLabel.toString()) && token[0].equals(tarLabel.toString()) ) {
						System.err.println("Edge already exists");
						return;
					}
				}
			}
		}
		
		if (found1==false || found2==false) {
			System.err.println("Vertices not found");
			return;
		}
		edges.add(srcLabel+" "+tarLabel);
        addtoMatrix();
        setMatrix();
    } // end of addEdge()

    public void setMatrix() {
        grapher[0][0] = "";
        for (int j = 0; j < edges.size(); j++) {
            if (grapher[0][j + 1].equals(edges.get(j))) {
                for (int x = 0; x < vert.size(); x++) {
                    if (grapher[x + 1][0].equals(vert.get(x).toString()) && grapher[0][j + 1].contains(vert.get(x).toString())) {
                        grapher[x + 1][j + 1] = "1";
                    } else {
                        grapher[x + 1][j + 1] = "0";
                    }
                }
            }
        }
    }

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();

        // Implement me!
		boolean exists = false;		
		for (int j = 0; j < edges.size(); j++) {
			if (grapher[0][j+1].contains(vertLabel.toString()) && grapher[vert.indexOf(vertLabel)+1][j+1] == "1") {
				String token[];
				token = grapher[0][j+1].split(" ");
				if (!token[0].equals(vertLabel.toString()) && !neighbours.contains(token[0])) {
					neighbours.add(vert.get(vert.indexOf(token[0])));
				} else if (!token[1].equals(vertLabel.toString()) && !neighbours.contains(token[1])) {
					neighbours.add(vert.get(vert.indexOf(token[1])));
				}
				exists =true;
			}
		}
		if (exists == false) {
			System.out.print("no vertex found: ");
		}
        return neighbours;
    } // end of neighbours()


    public void removeVertex(T vertLabel) {
        // Implement me!
		boolean removed = false;
		String token[];
		if (vert.size() > edges.size()) {
			for (int x = 0; x < vert.size(); x++) {
				if (vert.get(x).equals(vertLabel)) {
					vert.remove(x);
					removed = true;
				}
				if (x<edges.size()) {
					token = edges.get(x).split(" ");
					if (token[0].equals(vertLabel.toString()) || token[1].equals(vertLabel.toString())) {
						edges.remove(x);
					}
				}
			}
		} else {
			for (int x = 0; x < edges.size(); x++) {
				if (edges.get(x).contains(vertLabel.toString())) {
						edges.remove(x);
					}
				if (x<vert.size()) {
					if (vert.get(x).equals(vertLabel)) {
					vert.remove(x);
					removed =true;
				}
				}
			}
		}
		if (removed == true) {
			/*rearrange*/
			addtoMatrix();
			setMatrix();
		} else {
			System.err.println("failed to remove: vertLabel");	
		}
    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!
		boolean removed = false;
        for (int i = 0; i < edges.size(); i++) {
            if (grapher[0][i + 1].equals(tarLabel.toString()+" "+srcLabel.toString()) || grapher[0][i + 1].equals(srcLabel.toString()+" "+tarLabel.toString())) {
                grapher[0][i + 1] = "0";
                edges.remove(i);
				removed = true;
            }
        }
		 /*sort in order*/
		if (removed == true) {
         	addtoMatrix();
         	setMatrix();
		} else {
			System.err.println("Edge does not exists");	
		}
    } // end of removeEdges()


    public void printVertices(PrintWriter os) {
        // Implement me!
        for (int i = 0; i < vert.size(); i++) {
            os.print(grapher[i + 1][0].toString() + " ");
        }
        os.flush();

    } // end of printVertices()


    public void printEdges(PrintWriter os) {
		String token[];
        for (int i = 0; i < edges.size(); i++) {
			token = grapher[0][i + 1].split(" ");
			os.print(token[1] + " " + token[0] + " \n");
			os.print(token[0] + " " + token[1] + " \n");
        }
        os.flush();
		/*TESTS FOR CHECKING MATRIX
		for (int i =0; i <vert.size()+1;i++) {
			
			for(int j =0; j <edges.size()+1;j++) {
				System.out.print(grapher[i][j]);
			}
			System.out.println();	
		}*/
    } // end of printEdges()


    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        // Implement me!
		ArrayList<String> neighbours = new ArrayList<>();
        int distance = 0;
        int x = 0;
        boolean exists1 = false;
		boolean exists2 = false;
        String findtar = vertLabel2.toString();
        String findsrc = vertLabel1.toString();
		String token[];
		
        for (int i = 0; i < edges.size(); i++) {
			token = grapher[0][i + 1].split(" ");
            if (token[1].equals(vertLabel2.toString()) || token[0].equals(vertLabel1.toString())) {
                exists1 = true;
            }
			if (token[1].equals(vertLabel2.toString()) || token[0].equals(vertLabel1.toString())) {
				exists2 = true;
			}
            if (token[1].equals(vertLabel2.toString()) && token[0].equals(vertLabel1.toString()) || token[0].equals(vertLabel2.toString()) && token[1].equals(vertLabel1.toString())) {
                return 1;
            }
        }
		while (exists1 && exists2) {
			for (int i=0; i<vert.size();i++) {
				for (int j=0; j<edges.size();j++) {
					if (grapher[i+1][j+1] == "1" && grapher[0][j+1].contains(findsrc)) {
						distance++;
						token = grapher[0][j+1].split(" ");
						if (!token[1].equals(findsrc)) {
							findsrc = token[1]; 
						} else {
							findsrc = token[0]; 
						}
						if (grapher[0][j+1].equals(findsrc+" "+findtar) || findsrc.equals(findtar)) {
							return distance;
						}
					}
				}
			}
			x++;
			if (x >=50) {
               break;
           }
		}
		if (exists1 == false || exists2 ==false) {
			System.err.println("vertices don't exist: ");
		}
        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()
} // end of class IndMatrix