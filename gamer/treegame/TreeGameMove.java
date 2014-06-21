package gamer.treegame;

import gamer.def.Move;

public final class TreeGameMove implements Move<TreeGame> {
  final Node node;

  TreeGameMove(Node node) {
    this.node = node;
  }
}