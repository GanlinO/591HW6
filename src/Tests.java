import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class Tests {
    @Test
    void testEvilHangman(){
        EvilHangman newGame = new EvilHangman("testDict1.txt");
        assertEquals(10, newGame.getWordList().size());
        assertEquals(5, newGame.getMinLengthInWordList());
        assertEquals(11, newGame.getMaxLengthInWordList());
    }

    @Test
    void testDynamicSolution1() {
        EvilHangman newGame = new EvilHangman("testDict1.txt");
        DynamicSolution newSolution = new DynamicSolution(newGame.getWordList(), newGame.getMinLengthInWordList(), newGame.getMaxLengthInWordList());
        ArrayList<Integer> possibleSolutionLength = new ArrayList<>();
        for (int i = 4; i <= 11; i++){
            possibleSolutionLength.add(i);
        }
        assertTrue(possibleSolutionLength.contains(newSolution.getSolutionLength()));
    }

    @Test
    void testDynamicSolution2() {
        EvilHangman newGame = new EvilHangman("testDict2.txt");
        DynamicSolution newSolution = new DynamicSolution(newGame.getWordList(), newGame.getMinLengthInWordList(), newGame.getMaxLengthInWordList());

        assertEquals(5, newSolution.getSolutionLength());
        assertEquals("-----", newSolution.getPartialSolution());

        HashMap<String, ArrayList> expectedWordFamilies = new HashMap<>();
        expectedWordFamilies.put("-----", newGame.getWordList());

        assertEquals(expectedWordFamilies, newSolution.getWordFamilies());
    }

    @Test
    void testUpdateRelatedFunctions1() {
        EvilHangman newGame = new EvilHangman("testDict2.txt");
        DynamicSolution newSolution = new DynamicSolution(newGame.getWordList(), 5, 5);

        newSolution.updateWordFamilies("b");

        HashMap<String, ArrayList> expectedWordFamilies = new HashMap<>();
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("baaed");
        expectedWordFamilies.put("b----", list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("babas");
        list2.add("babel");
        list2.add("babes");
        list2.add("babka");
        expectedWordFamilies.put("b-b--", list2);

        ArrayList<String> list3 = new ArrayList<>();
        list3.add("writs");
        list3.add("wrong");
        list3.add("wrote");
        expectedWordFamilies.put("-----", list3);

        assertEquals(expectedWordFamilies, newSolution.getWordFamilies());

        assertTrue(newSolution.updatePartialSolution());
        assertEquals("b-b--", newSolution.getPartialSolution());

        newSolution.updateRemainingWordList();
        assertEquals(list2, newSolution.getRemainingWordList());

        newSolution.updateWordFamilies("a");

        HashMap<String, ArrayList> expectedWordFamilies1 = new HashMap<>();

        ArrayList<String> list4 = new ArrayList<>();
        list4.add("babas");
        expectedWordFamilies1.put("baba-", list4);

        ArrayList<String> list5 = new ArrayList<>();
        list5.add("babel");
        list5.add("babes");
        expectedWordFamilies1.put("bab--", list5);

        ArrayList<String> list6 = new ArrayList<>();
        list6.add("babka");
        expectedWordFamilies1.put("bab-a", list6);

        assertEquals(expectedWordFamilies1, newSolution.getWordFamilies());

        assertTrue(newSolution.updatePartialSolution());
        assertEquals("bab--", newSolution.getPartialSolution());

        newSolution.updateRemainingWordList();
        assertEquals(list5, newSolution.getRemainingWordList());

        newSolution.updateWordFamilies("c");

        HashMap<String, ArrayList> expectedWordFamilies2 = new HashMap<>();
        expectedWordFamilies2.put("bab--", list5);

        assertEquals(expectedWordFamilies2, newSolution.getWordFamilies());

        assertFalse(newSolution.updatePartialSolution());

        newSolution.updateWordFamilies("e");
        HashMap<String, ArrayList> expectedWordFamilies3 = new HashMap<>();
        expectedWordFamilies3.put("babe-", list5);

        assertEquals(expectedWordFamilies3, newSolution.getWordFamilies());

        assertTrue(newSolution.updatePartialSolution());
        assertEquals("babe-", newSolution.getPartialSolution());

        newSolution.updateRemainingWordList();
        assertEquals(list5, newSolution.getRemainingWordList());

        newSolution.updateWordFamilies("f");
        assertEquals(expectedWordFamilies3, newSolution.getWordFamilies());
        assertFalse(newSolution.updatePartialSolution());

        newSolution.updateWordFamilies("l");
        HashMap<String, ArrayList> expectedWordFamilies4 = new HashMap<>();
        ArrayList<String> list7 = new ArrayList<>();
        list7.add("babel");
        expectedWordFamilies4.put("babel", list7);
        ArrayList<String> list8 = new ArrayList<>();
        list8.add("babes");
        expectedWordFamilies4.put("babe-", list8);
        assertEquals(expectedWordFamilies4, newSolution.getWordFamilies());

        assertTrue(newSolution.updatePartialSolution());
        assertEquals("babel", newSolution.getPartialSolution());

        newSolution.updateRemainingWordList();
        assertEquals(list7, newSolution.getRemainingWordList());

    }

    @Test
    void testUpdateRelatedFunctions2() {
        EvilHangman newGame = new EvilHangman("testDict3.txt");
        DynamicSolution newSolution = new DynamicSolution(newGame.getWordList(), 5, 5);

        newSolution.updateWordFamilies("b");

        HashMap<String, ArrayList> expectedWordFamilies = new HashMap<>();
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("baaed");
        expectedWordFamilies.put("b----", list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("babas");
        list2.add("babel");
        list2.add("babes");
        list2.add("babka");
        expectedWordFamilies.put("b-b--", list2);

        ArrayList<String> list3 = new ArrayList<>();
        list3.add("writs");
        list3.add("wrong");
        list3.add("wrote");
        list3.add("write");
        expectedWordFamilies.put("-----", list3);

        assertEquals(expectedWordFamilies, newSolution.getWordFamilies());

        assertFalse(newSolution.updatePartialSolution());
        assertEquals("-----", newSolution.getPartialSolution());

        newSolution.updateRemainingWordList();
        assertEquals(list3, newSolution.getRemainingWordList());
    }
}
