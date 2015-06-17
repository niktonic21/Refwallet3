package sk.upjs.ics.refwallet;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maťo21 on 7. 6. 2015.
 */
public enum ZapasyDao {
    INSTANCE;

    private List<Zapas> zapasy = new LinkedList<Zapas>();

    private long idGenerator = 0;

    private ZapasyDao() {
        Zapas prvy = new Zapas(21,"12.12.2014","EXS","Kosice","Kosice","Poprad","Jura","Lauff","Jobbagy","Bogdan","Orszag","Kontsek","23:00","12:00","Kosice","Poprad",83);
        saveOrUpdate(prvy);


    }

    public void saveOrUpdate(Zapas zapas) {
        if (zapas.getId() == null) {
            zapas.setId(idGenerator++);
            zapasy.add(zapas);
        } else {
            Iterator<Zapas> iterator = zapasy.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Zapas t = iterator.next();
                if (t.getId().equals(zapas.getId())) {
                    iterator.remove();
                    break;
                }
                index++;
            }
            zapasy.add(index, zapas);
        }
    }

    public List<Zapas> list() {
        return new LinkedList<Zapas>(this.zapasy);
    }

    public Zapas getZapas(long zapasId) {
        for (Zapas zapas : this.zapasy) {
            if (zapas.getId() == zapasId) {
                return zapas;
            }
        }
        return null;
    }

    public void delete(Zapas zapas) {
        Iterator<Zapas> iterator = this.zapasy.iterator();
        while(iterator.hasNext()) {
            Zapas t = iterator.next();
            if(t.getId() == zapas.getId()) {
                iterator.remove();
            }
        }
    }
}
