package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.EnumMap;
import java.util.Map;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the management of player lives.
 */
class PlayerLivesTest {

    /**
     * Player under test.
     */
    private Player player;

    /**
     * Ghost responsible for the player's death.
     */
    private Ghost ghost;

    /**
     * Creates a player before each test.
     */
    @BeforeEach
    void setUp() {
        Map<Direction, Sprite> sprites = new EnumMap<>(Direction.class);

        for (Direction direction : Direction.values()) {
            sprites.put(direction, mock(Sprite.class));
        }

        AnimatedSprite deathSprite = mock(AnimatedSprite.class);
        player = new Player(sprites, deathSprite);
        ghost = mock(Ghost.class);
    }

    /**
     * Verifies that a player starts with three lives.
     */
    @Test
    void playerStartsWithThreeLives() {
        assertThat(player.getLives()).isEqualTo(3);
        assertThat(player.hasLivesRemaining()).isTrue();
    }

    /**
     * Verifies that dying removes one life.
     */
    @Test
    void dyingRemovesOneLife() {
        player.die(ghost);

        assertThat(player.getLives()).isEqualTo(2);
        assertThat(player.hasLivesRemaining()).isTrue();
        assertThat(player.isAlive()).isFalse();
        assertThat(player.getKiller()).isEqualTo(ghost);
    }

    /**
     * Verifies that the player has no remaining lives after three deaths.
     */
    @Test
    void playerHasNoLivesAfterThreeDeaths() {
        player.die(ghost);
        player.die(ghost);
        player.die(ghost);

        assertThat(player.getLives()).isZero();
        assertThat(player.hasLivesRemaining()).isFalse();
    }

    /**
     * Verifies that the number of lives never becomes negative.
     */
    @Test
    void livesNeverBecomeNegative() {
        player.loseLife();
        player.loseLife();
        player.loseLife();
        player.loseLife();

        assertThat(player.getLives()).isZero();
    }
}
