package pl.edu.pw.ee;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestCommonSubsequenceTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentsToConstructorTest() {

        LongestCommonSubsequence l = new LongestCommonSubsequence(null,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyStringArgumentsToConstructorTest() {

        LongestCommonSubsequence l = new LongestCommonSubsequence("", "");
        assertEquals("napi", l.findLCS());
    }

    @Test
    public void findAndDisplayLcsTest1() {

        LongestCommonSubsequence l = new LongestCommonSubsequence("kanapki", "napisy");
        assertEquals("napi", l.findLCS());
        l.display();
    }

    @Test
    public void findAndDisplayLcsTest2() {

        LongestCommonSubsequence l = new LongestCommonSubsequence("często_z_odkrywaniem", "rzeczy_nie_trzeba\\n_się_spieszyć");
        assertEquals("cz__raie", l.findLCS());
        l.display();
    }

    @Test
    public void findAndDisplayLcsTabAndNewLineSpecialCharacters() {

        LongestCommonSubsequence l = new LongestCommonSubsequence("bam\\tbus\\n", "banany\\t\\n");
        assertEquals("ba\t\n", l.findLCS());
        l.display();
    }

    @Test
    public void findAndDisplayLcsSameWord() {

        LongestCommonSubsequence l = new LongestCommonSubsequence("często_z_odkrywaniem", "często_z_odkrywaniem");
        assertEquals("często_z_odkrywaniem", l.findLCS());
        l.display();
    }

    @Test
    public void findAndDisplayLcsDifferentWords() {

        LongestCommonSubsequence l = new LongestCommonSubsequence("Justin", "Beber");
        assertEquals("", l.findLCS());
        l.display();
    }

    @Test
    public void findAndDisplayLcsWordsContainingThemself() {

        LongestCommonSubsequence l = new LongestCommonSubsequence("Justin", "JustinBieber");
        assertEquals("Justin", l.findLCS());
        l.display();
    }

    @Test
    public void findAndDisplayLcsLongWords() {

        LongestCommonSubsequence l = new LongestCommonSubsequence("JustinBieberIsn'tAsGoodAsJustinBieber", "JustinBieberIsTotallyTheBestBarberInTheWholeWorldIDon'tThingThatMsrProfessorWillReadThisMessage");
        assertEquals("JustinBieberIstsoodtiniee", l.findLCS());
        l.display();
    }
}
