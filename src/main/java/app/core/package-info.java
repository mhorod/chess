/**
 * Core is the common interface between users and games.
 * <p>
 * It's split into two parts:
 * <ul>
 *  <li> game - which provides interface that every game should implement </li>
 *  <li> interactor - provides a way for players to send and receive information from a game </li>
 * </ul>
 * <p>
 * Inside this package you will see &lt;M extends Move&lt;P&gt;&gt;, P extends Piece> in generic type parameters.
 * It's our solution to make interaction polymorphic without losing information about types.
 * This also ensures that move and piece moves match and can be used together.
 */
package app.core;