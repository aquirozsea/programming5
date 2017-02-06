package programming5.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andres on 2/6/17.
 */
public class GraphTraversalsTest {

    static Map<String, Object> graph;

    @BeforeClass
    public static void initGraph() {
        graph = new HashMap<>();
        graph.put("name", "root");
        graph.put("weight", 0);
        Map<String, Object> child1 = new HashMap<>();
        child1.put("name", "child1");
        child1.put("weight", 1);
        Map<String, Object> child11 = new HashMap<>();
        child11.put("name", "child11");
        child11.put("weight", 11);
        Map<String, Object> child12 = new HashMap<>();
        child12.put("name", "child12");
        child12.put("weight", 12);
        Map<String, Object> child13 = new HashMap<>();
        child13.put("name", "child13");
        child13.put("weight", 13);
        child1.put("children", Arrays.asList(child11, child12, child13));
        Map<String, Object> child2 = new HashMap<>();
        child2.put("name", "child2");
        child2.put("weight", 2);
        Map<String, Object> child21 = new HashMap<>();
        child21.put("name", "child21");
        child21.put("weight", 21);
        child2.put("children", Collections.singletonList(child21));
        graph.put("children", Arrays.asList(child1, child2));
    }

    public static NodeAdjacencyIterator<Map<String, Object>> createAdjacencyIterator(Map<String, Object> node) {
        return new NodeAdjacencyIterator<Map<String, Object>>(node) {

            public Iterator<Map<String, Object>> getChildIterator() {
                return ((List<Map<String, Object>>) node.getOrDefault("children", Collections.emptyList())).iterator();
            }

        };
    }

    @Test
    public void testBasicDFS_iteratorOverride() {
        final List<String> discoveredList = new ArrayList<>();
        final List<String> finishedOrder = new ArrayList<>();
        GraphTraversals.depthFirstWalk(
                graph,
                node -> new NodeAdjacencyIterator<Map<String, Object>>(node) {

                    Iterator<Map<String, Object>> iterator = null;

                    @Override
                    public Iterator<Map<String, Object>> getChildIterator() {
                        return new Iterator<Map<String, Object>>() {
                            @Override
                            public boolean hasNext() {
                                if (iterator == null) {
                                    if (node.containsKey("children")) {
                                        List<Map<String, Object>> children = ((List<Map<String, Object>>) node.getOrDefault("children", Collections.emptyList()));
                                        iterator = children.iterator();
                                    }
                                    else return false;
                                }
                                return iterator.hasNext();
                            }

                            @Override
                            public Map<String, Object> next() {
                                if (!this.hasNext()) return null;
                                return iterator.next();
                            }
                        };
                    }
                },
                node -> {
                    discoveredList.add(((String) node.get("name")));
                },
                edge -> {},
                edge -> {},
                node -> {
                    finishedOrder.add(((String) node.get("name")));
                }
        );
        assertEquals(
                Stream.of("root", "child1", "child11", "child12", "child13", "child2", "child21").collect(Collectors.toList()),
                discoveredList
        );
        assertEquals(
                Stream.of("child11", "child12", "child13", "child1", "child21", "child2", "root").collect(Collectors.toList()),
                finishedOrder
        );
    }

    @Test
    public void testBasicDFS() {
        final List<String> discoveredList = new ArrayList<>();
        final List<String> finishedOrder = new ArrayList<>();
        GraphTraversals.depthFirstWalk(
                graph,
                node -> createAdjacencyIterator(node),
                node -> discoveredList.add(((String) node.get("name"))),
                edge -> {},
                edge -> {},
                node -> finishedOrder.add(((String) node.get("name")))
        );
        assertEquals(
                Stream.of("root", "child1", "child11", "child12", "child13", "child2", "child21").collect(Collectors.toList()),
                discoveredList
        );
        assertEquals(
                Stream.of("child11", "child12", "child13", "child1", "child21", "child2", "root").collect(Collectors.toList()),
                finishedOrder
        );
    }

    @Test
    public void testUpdatingDFS() {
        final Map<String, Integer> totalWeights = new HashMap<>();
        GraphTraversals.depthFirstWalk(
                graph,
                node -> createAdjacencyIterator(node),
                node -> totalWeights.put(((String) node.get("name")), 0),
                edge -> {},
                edge -> {
                    String updatingNode = ((String) edge.getParent().get("name"));
                    int currentTotal = totalWeights.getOrDefault(updatingNode, 0);
                    totalWeights.put(updatingNode, currentTotal + ((Integer) edge.getChild().get("weight")));
                },
                node -> {}
        );
        assertEquals(3, totalWeights.get("root").intValue());
        assertEquals(36, totalWeights.get("child1").intValue());
        assertEquals(21, totalWeights.get("child2").intValue());
        assertEquals(0, totalWeights.get("child12").intValue());
        assertEquals(0, totalWeights.get("child21").intValue());
    }

    @Test
    public void testPreorder() {
        assertEquals(
                Arrays.asList("root", "child1", "child11", "child12", "child13", "child2", "child21"),
                GraphTraversals.graphPreOrder(graph, node -> createAdjacencyIterator(node)).stream()
                        .map(node -> (String) node.get("name"))
                        .collect(Collectors.toList())
        );
    }

    @Test
    public void testPostorder() {
        assertEquals(
                Arrays.asList("child11", "child12", "child13", "child1", "child21", "child2", "root"),
                GraphTraversals.graphPostOrder(graph, node -> createAdjacencyIterator(node)).stream()
                        .map(node -> (String) node.get("name"))
                        .collect(Collectors.toList())
        );
    }
}
