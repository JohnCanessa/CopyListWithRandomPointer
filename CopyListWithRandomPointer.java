import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;


/**
 * LeetCode 138. Copy List with Random Pointer
 * https://leetcode.com/problems/copy-list-with-random-pointer/
 */


/**
 * Definition for a Node.
 */
class Node {

    // **** class members ****
    int     val;
    Node    next;
    Node    random;

    // **** constructor ****
    public Node(int val) {
        this.val    = val;
        this.next   = null;
        this.random = null;
    }

    // **** ****
    @Override
    public String toString() {
        String r = null;
        if (this.random == null)
            r = "null";
        else
            r = "" + random.val;
        return "(v: " + this.val + " r: " + r + ")";
    }
}


/**
 * 
 */
public class CopyListWithRandomPointer {


    /**
     * Build linked list from specified array of strings.
     * 
     * !!! NOT PART OF SOLUTION !!!
     */
    static Node buildLinkedList(String[] strArr) {

        // **** sanity check(s) ****
        if (strArr == null || strArr[0].equals(""))
            return null;

        // **** initialization ****
        Node[] nodes        = new Node[strArr.length];
        Integer[] randoms   = new Integer[strArr.length];
        Node head           = null;
        Node tail           = head;

        // **** traverse array of strings ****
        for (int i = 0; i < strArr.length; i++) {

            // **** split ****
            String[] s = strArr[i].split(",");

            // **** extract val ****
            int val = Integer.parseInt(s[0]);

            // **** extract random index ****
            Integer randIndex = null;
            if (!s[1].equals("null"))
                randIndex = Integer.parseInt(s[1]);

            // **** save random reference ****
            randoms[i] = randIndex;

            // **** create node ****
            Node node = new Node(val);
            
            // **** insert node to list (at tail) ****
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }

            // **** save the node in the array ****
            nodes[i] = node;
        }

        // **** set random field in all nodes ****
        for (int i = 0; i < nodes.length; i++) {
            if (randoms[i] != null)
                nodes[i].random = nodes[randoms[i]];
        }

        // **** return head of linked list ****
        return head;
    }


    /**
     * Display linked list.
     * 
     * !!! NOT PART OF SOLUTION !!!
     */
    static void display(Node head) {
    
        // **** sanity check(s) ****
        if (head == null)
            return;
    
        // **** traverse the link list ****
        for (Node p = head; p != null; p = p.next) {
            System.out.print(p.toString());
            if (p.next != null)
                System.out.print("->");
        }
    }


    /**
     * Return a deep copy of the linked list.
     * Does not include random pointer.
     * 
     * !!! NOT PART OF SOLUTION !!!
     */
    static Node copyLinkedList(Node head) {

        // **** sanity check(s) ****
        if (head == null)
            return null;

        // **** initialization ****
        HashMap<Node, Node> hm  = new HashMap<>();

        Node q                  = new Node(head.val);
        hm.put(head, q);
        Node ll                 = q;

        // **** traverse the linked lists O(n) ****
        for (Node p = head; p != null; p = p.next, q = q.next) {

            // **** store reference (pointer) ****
            Node nextNode = p.next;

            // **** create & update next pointer (if needed) ****
            if (nextNode != null) {

                // **** create new next node (if needed) ****
                if (!hm.containsKey(nextNode)) {
                    hm.put(nextNode, new Node(nextNode.val));
                }

                // **** point to next node ****
                q.next = hm.get(nextNode);
            }
        }

        // **** return deep copy of linked list ****
        return ll;
    }


    /**
     * Return a deep copy of the list.
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions.
     * Memory Usage: 38.7 MB, less than 58.54% of Java online submissions
     */
    static Node copyRandomList(Node head) {

        // **** sanity check(s) ****
        if (head == null)
            return null;

        // **** initialization ****
        HashMap<Node, Node> hm  = new HashMap<>();

        Node q                  = new Node(head.val);
        hm.put(head, q);
        Node dc                 = q;

        // **** traverse the linked lists O(n) ****
        for (Node p = head; p != null; p = p.next, q = q.next) {

            // **** store pointers (references) ****
            Node randNode = p.random; 
            Node nextNode = p.next;

            // **** create & update random pointer (if needed) ****
            if (randNode != null) {

                // **** create new random node (if needed) ****
                if (!hm.containsKey(randNode)) {
                    hm.put(randNode, new Node(randNode.val));
                }

                // **** point to next random node ****
                q.random = hm.get(randNode);
            }

            // **** create & update next pointer (if needed) ****
            if (nextNode != null) {

                // **** create new next node (if needed) ****
                if (!hm.containsKey(nextNode)) {
                    hm.put(nextNode, new Node(nextNode.val));
                }

                // **** point to next node ****
                q.next = hm.get(nextNode);
            }
        }

        // **** return pointer to deep copy ****
        return dc;
    }


    /**
     * Test scaffolding
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // **** read values for linked list ****
        String str = br.readLine().trim();

        // **** close buffered reader ****
        br.close();
        
        // **** remove start and end brackets ****
        str = str.substring(1, str.length() - 1);

        // **** split into pairs of values ****
        String[] strArr = str.split("\\],\\[");

        // ???? ????
        System.out.println("main <<< strArr: " + Arrays.toString(strArr));

        // **** build linked list of nodes ****
        Node head = buildLinkedList(strArr);

        // ???? display linked list ????
        System.out.print("main <<<   head: ");
        display(head);
        System.out.println();

        // **** deep copy of linked list (no random pointers) **** 
        Node ll = copyLinkedList(head);

        // ???? display deep copy linked list ????
        System.out.print("main <<<     ll: ");
        display(ll);
        System.out.println();

        // **** deep copy of linked list (include random pointers) ****
        Node dc = copyRandomList(head);

        // ???? display deep copy linked list ????
        System.out.print("main <<< output: ");
        display(dc);
        System.out.println();
    }
}