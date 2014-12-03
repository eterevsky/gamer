package gamer.players;

import gamer.def.Game;
import gamer.def.GameState;

import java.util.Collection;
import java.util.Iterator;

public class NaiveMonteCarlo<G extends Game> extends GenericPlayer<G> {
  static class Selector<G extends Game> implements Node.Selector<G> {
    Iterator<Node<G>> childrenIt = null;

    public void setNode(Node<G> node) {}

    public synchronized Node<G> select(
        Collection<Node<G>> children, long totalSamples) {
      if (childrenIt == null || !childrenIt.hasNext())
        childrenIt = children.iterator();

      return childrenIt.next();
    }

    public boolean shouldCreateChildren() {
      return true;
    }

    public synchronized LeafSelector<G> newChildSelector() {
      return new LeafSelector<G>();
    }

    public void childUpdated(Node<G> child, long totalSamples) {}
  }

  @Override
  protected Node<G> getRoot(GameState<G> state) {
    return new Node<G>(null, state, null, new Selector<G>(), nodeContext);
  }
}