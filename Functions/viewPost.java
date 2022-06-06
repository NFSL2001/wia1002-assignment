package Functions;

public class viewPost {
    
    public void display() {
        PostTree tree = new PostTree();
        WaitingPost queue = new WaitingPost();
        queue.addPost(tree, new Post(1234, "hi world"));
        Post parent = tree.findPost(1234);
        queue.addPost(tree, new Post(1235, parent, "hi world 2"));
        queue.addPost(tree, new Post(4, "i am ok"));
        queue.addPost(tree, new Post(5, "i am ok"));
        Post answerToEverything = new Post(42, "answer to everything");
        queue.addPost(tree, answerToEverything);
        queue.addPost(tree, new Post(43, answerToEverything, "really?"));
        queue.addPost(tree, new Post(44, answerToEverything, "It's fake!"));
        queue.addPost(tree, new Post(45, answerToEverything, "hmmmm...."));
        Post reply1 = tree.findPost(45);
        queue.addPost(tree, new Post(46, reply1, "What you think #45?"));
        reply1 = tree.findPost(46);
        queue.addPost(tree, new Post(47, reply1, "Maybe."));
        queue.addPost(tree, new Post(48, reply1, "Maybe too."));
        System.out.println("Is queue empty? "+queue.isEmpty());
        for(Post p: tree.getAllPost()){
            System.out.println(p);
        }
        System.out.println("=== Removing post #4...");
        System.out.println(tree.removePost(4));
        System.out.println("=== Removing post #1234...");
        System.out.println(tree.removePost(1234));
        for(Post p: tree.getAllPost()){
            System.out.println(p);
        }
        System.out.println("=== Removing post #45...");
        System.out.println(tree.removePost(45));
        for(Post p: tree.getAllPost()){
            System.out.println(p);
        }
        System.out.println(answerToEverything.getChildrenSize());
    }
}
