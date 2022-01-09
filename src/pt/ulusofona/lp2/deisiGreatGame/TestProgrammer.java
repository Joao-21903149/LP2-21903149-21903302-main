package pt.ulusofona.lp2.deisiGreatGame;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class TestProgrammer {

    @Test
    public void testTotalProgammer(){
        Programmer p = new Programmer();
        p.setId(2);
        Assert.assertEquals(2,p.getId());
        p.setName("Joao");
        Assert.assertEquals("Joao",p.getName());

        p.setColor(ProgrammerColor.BLUE);
        Assert.assertEquals(ProgrammerColor.BLUE,p.getColor());

        p.move(3);
        Assert.assertEquals(p.getPos(),4);

        ArrayList<String> lings = new ArrayList<String>();
        lings.add("Java");
        p.setLinguages(lings);


        String expected = "2 | Joao | 4 | No tools | Java | Em Jogo";
        Assert.assertEquals(expected,p.toString());

        System.out.println("ola");

    }

}
