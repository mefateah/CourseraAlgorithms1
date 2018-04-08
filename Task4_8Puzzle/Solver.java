import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Solver {
    private SearchNode solution;
    private boolean isSolvable = false;
    
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode predecessor;
        
        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.predecessor = prev;
        }
    }
    
    private Comparator<SearchNode> manhattanComparator() {
        return new Comparator<SearchNode>() {
            
            public int compare(SearchNode node1, SearchNode node2) {
                int a = node1.board.manhattan() + node1.moves;
                int b = node2.board.manhattan() + node2.moves;
                
                if (a > b) {
                    return 1;
                }
                if (a < b) {
                    return -1;
                }
                return 0;
            }
        };
    }
    
    private SearchNode step(MinPQ<SearchNode> queue) {
        SearchNode node = queue.delMin();
        if (node.board.isGoal()) {
            return node;
        }
        for (Board b : node.board.neighbors()) {
            queue.insert(new SearchNode(b, node.moves + 1, node));
        }
        return null;
    }
    
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        MinPQ<SearchNode> queue = new MinPQ<SearchNode>(manhattanComparator());
        // one more queue for board's twin
        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>(manhattanComparator());
        
        queue.insert(new SearchNode(initial, 0, null));
        twinQueue.insert(new SearchNode(initial.twin(), 0, null));
        while (true) {
            SearchNode node = queue.delMin();
            if (node.board.isGoal()) {
                this.solution = node;
                this.isSolvable = true;
                break;
            }
            //StdOut(node.board.toString());
            for (Board b : node.board.neighbors()) {
                if (node.predecessor == null || !b.equals(node.predecessor.board)) {
                    queue.insert(new SearchNode(b, node.moves + 1, node));
                }
            }
            
            // twin
            SearchNode twinNode = twinQueue.delMin();
            if (twinNode.board.isGoal()) {
                this.isSolvable = false;
                break;
            }
            for (Board b : twinNode.board.neighbors()) {
                if (twinNode.predecessor == null || !b.equals(twinNode.predecessor.board)) {
                    twinQueue.insert(new SearchNode(b, twinNode.moves + 1, twinNode));
                }
            }
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return solution.moves;
    }

    public Iterable<Board> solution() {
        if (isSolvable == false) {
            return null;
        } else {
            List<Board> path = new ArrayList<Board>();
            SearchNode curr = solution;
            while (curr != null) {
                path.add(curr.board);
                curr = curr.predecessor;
            }
            Collections.reverse(path);
            return path;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial.twin());
        StdOut.println(initial);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}