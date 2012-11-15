package dao;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import commons.Entity;

import domain.Word;
import domain.WordType;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {config.Appcontext.class})
@ActiveProfiles (profiles = {"test"})
public class DaoComponentTest {
    @Autowired
    private DaoComponent daoComponent;

    /* Depends on contents of file environments/hsqldb/init.sql */
    private void initTestWords (ArrayList <TestWord> dst) {
        assertTrue (dst.size () == 0);
        dst.add (new TestWord ("krant", WordType.N));
        dst.add (new TestWord ("oppervlakte", WordType.N));
        dst.add (new TestWord ("schrijven", WordType.V));
        dst.add (new TestWord ("vallen", WordType.V));
        dst.add (new TestWord ("opstaan", WordType.V));
        dst.add (new TestWord ("berekenen", WordType.V));
        dst.add (new TestWord ("kort", WordType.A));
        dst.add (new TestWord ("lelijk", WordType.A));
        dst.add (new TestWord ("slecht", WordType.A));
        dst.add (new TestWord ("in", WordType.P));
        dst.add (new TestWord ("op", WordType.P));
        dst.add (new TestWord ("met", WordType.P));
        dst.add (new TestWord ("zonder", WordType.P));
        dst.add (new TestWord ("bij", WordType.P));
        Collections.sort (dst);
    }

    @Test
    public void test () {
        Word w;

        ArrayList <TestWord> testset = new ArrayList ();
        initTestWords (testset);

        /* Test count. */
        DataFilter dataFilter = new DataFilter ();
        assertTrue (daoComponent.count (Word.class, DataFilter.EMPTY_DATA_FILTER) == testset.size ());
        dataFilter.replace ("type", WordType.N);
        assertTrue (daoComponent.count (Word.class, dataFilter) == 2);
        dataFilter.replace ("type", WordType.V);
        assertTrue (daoComponent.count (Word.class, dataFilter) == 4);
        dataFilter.replace ("type", WordType.A);
        assertTrue (daoComponent.count (Word.class, dataFilter) == 3);
        dataFilter.replace ("type", WordType.P);
        assertTrue (daoComponent.count (Word.class, dataFilter) == 5);
        dataFilter.replace ("code", "met");
        assertTrue (daoComponent.count (Word.class, dataFilter) == 1);
        dataFilter.replace ("type", WordType.A);
        assertTrue (daoComponent.count (Word.class, dataFilter) == 0);

        /* Test reading the entire list. */
        List <Word> list = (List<Word>) (List<?>) daoComponent.read (Word.class, DataFilter.EMPTY_DATA_FILTER, 0, Integer.MAX_VALUE, "code", true);
        assertTrue (list.size () == testset.size ());
        ListIterator <Word> iter = list.listIterator ();
        int i = 0;
        while (iter.hasNext ()) {
            w = iter.next ();
            TestWord tw = testset.get (i);
            assertTrue (w.getCode ().equals (tw.getCode ()));
            assertTrue (w.getType ().equals (tw.getType ()));
            tw.setId (w.getId ());
            ++i;
        }

        /* Test reading individual words. */
        for (TestWord tw: testset) {
            w = (Word) daoComponent.read (Word.class, tw.getId ());
            assertTrue (w.getId () == tw.getId ());
        }
        assertTrue (daoComponent.read (Word.class, 0) == null);

        /* Test writing a word. */
        w = new Word ();
        w.setCode ("goed");
        w.setType (WordType.A);
        assertTrue (w.getId () == 0);
        daoComponent.write (w);
        assertTrue (w.getId () != 0);
        assertTrue (daoComponent.read (Word.class, w.getId ()) != null);
        assertTrue (daoComponent.count (Word.class, DataFilter.EMPTY_DATA_FILTER) == testset.size () + 1);

        /* Test deleting a word. */
        daoComponent.delete (Word.class, w.getId ());
        assertTrue (daoComponent.read (Word.class, w.getId ()) == null);
        assertTrue (daoComponent.count (Word.class, DataFilter.EMPTY_DATA_FILTER) == testset.size ());
    }

    private static class TestWord extends Word implements Comparable <TestWord> {
        TestWord (String code, WordType type) {
            setCode (code);
            setType (type);
        }

        @Override
        public int compareTo (TestWord other) {
            return getCode ().compareTo (other.getCode ());
        }

        void setId (long val) {
            try {
                Field idfield = Entity.class.getDeclaredField ("id");
                idfield.setAccessible (true);
                idfield.setLong (this, val);
            } catch (Exception x) {
                throw new RuntimeException ("Could not set id.", x);
            }
        }
    }
}
