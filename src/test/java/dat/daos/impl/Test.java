package dat.daos.impl;

    import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;


    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Test {
        @BeforeEach
        void setUp() {
            System.out.println("Setting up test");
        }

        @AfterEach
        void tearDown() {
            System.out.println("Tearing down test");
        }

        @org.junit.jupiter.api.Test
        void testOneEqualsOne() {
            assertEquals(1, 1);
        }

        @org.junit.jupiter.api.Test
        void testTwoEqualsTwo() {
            assertEquals(2, 2);
        }

        @org.junit.jupiter.api.Test
        void testThreeEqualsThree() {
            assertEquals(3, 3);
        }

        @org.junit.jupiter.api.Test
        void testFourEqualsFour() {
            assertEquals(4, 4);
        }


    }




