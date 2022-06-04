/**
 * Core is the common interface between users and games.
 * <p>
 * It's split into two parts:
 * <ul>
 *  <li> game - which provides interface that every game should implement </li>
 *  <li> interactor - provides a way for players to send and receive information from a game </li>
 * </ul>
 * <p>
 * Inside this package you will see <M extends Move<P>, P extends Piece> in generic type parameters.
 * It's our solution to make interaction polymorphic without
 */
package app.core;