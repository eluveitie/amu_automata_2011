package pl.edu.amu.wmi.daut.re;

import java.util.List;
import java.util.ArrayList;

    /**
    * Dodaje fabrykę operatorFactory danego operatora. Napis id będzie identyfikatorem (nazwą)
    * operatora. Lista separators będzie listą napisów oddzielających argumenty i parametry,
    * lista ta musi mieć długość równą sumie liczby argumentów i parametrów powiększoną o jeden.
    * Pierwszy i ostatni element listy separators może być napisem pustym. Np. dla operatora
    * `KleeneStarOperator` i tradycyjnego zapisu wyrażeń regularnych separators będzie listą
    * dwuelementową, pierwszym elementem będzie napis pusty, drugim - napis "*". Z kolei dla
    * operatora RangeNumberOfOccurrencesOperator pierwszym elementem będzie napis pusty, drugim -
    * napis "{", trzecim - napis ",", czwartym - napis "}" (operator ma jeden argument i dwa
    * parametry).
    *
    * Identyfikator operatora jest dowolnym napisem, zwykle będziemy jakoś wykorzystywać któryś z
    * separatorów (ale nie jest to obowiązkowe), np.
    * dobrym identyfikatorem KleeneStarOperator
    * będzie gwiazdka.
    *
    * Priority to priorytet operatora.
    */
public class RegexpOperatorManager {

    /**
    * Reprezentuje dane operatora.
    */
    public static final class OperatorFactory {

        private String id;
        private List<String> separators = new ArrayList<String>();
        private int priority;
        private RegexpOperatorFactory operatorFactory;

        /**
        * Konstruktor 4 argumentowy.
        */
        private OperatorFactory(String id, RegexpOperatorFactory operatorFactory,
                List<String> separators, int priority) {

            this.id = id;
            this.priority = priority;
            this.operatorFactory = operatorFactory;
            this.separators.addAll(separators);
        }

        /**
        * Konstruktor 3 argumentowy.
        */
        private OperatorFactory(String id, RegexpOperatorFactory operatorFactory,
                List<String> separators) {

            this.id = id;
            this.priority = 0;
            this.operatorFactory = operatorFactory;
            this.separators.addAll(separators);
        }
    }

    private List<OperatorFactory> definedOperators = new ArrayList<OperatorFactory>();
    private OperatorFactory actualOperator;

     /**
     * Dodaje fabrykę operatorFactory danego operatora.
     */
    void addOperator(String id, RegexpOperatorFactory operatorFactory, List<String> separators,
            int priority) {

        actualOperator = new OperatorFactory(id, operatorFactory, separators, priority);
        definedOperators.add(actualOperator);
    }

    /**
     * Dodaje fabrykę operatorFactory danego operatora bez podanego priorytetu.
     */
    void addOperator(String id, RegexpOperatorFactory operatorFactory, List<String> separators) {

        actualOperator = new OperatorFactory(id, operatorFactory, separators);
        definedOperators.add(actualOperator);
    }


    /**
     * Zwraca listę separatorów dla operatora o identyfikatorze id.
     */
    List<String> getSeparators(String id) {

        List<String> returnedSeparators = new ArrayList<String>();

        for (OperatorFactory operator : definedOperators) {
            if (operator.id.equals(id))
                returnedSeparators.addAll(operator.separators);
        }

       if (returnedSeparators.isEmpty())
            return null;
       else return returnedSeparators;
    }


    /**
    * Zwraca fabrykę operatora o identyfikatorze id.
    */
    RegexpOperatorFactory getFactory(String id) {

        RegexpOperatorFactory returned = null;

        for (OperatorFactory operator : definedOperators) {
            if (operator.id.equals(id)) {
                  returned = operator.operatorFactory;
                  break;
            }
        }
       return returned;
    }


    /**
     * Zwraca priorytet operatora id.
     */
    int getPriority(String id) {
        int returned = -1;

        for (OperatorFactory operator : definedOperators) {
            if (operator.id.equals(id)) {
                 returned = operator.priority;
                 break;
            }
        }
        return returned;
    }


    /**
    * Zwraca listę identyfikatorów operatorów, których pierwszy separator to najdłuższy prefiks
    * napisu s. Jeśli żaden niepusty prefiks napisu s nie jest pierwszym separatorem, wówczas
    * powinna zwrócić listę wszystkich operatorów, których pierwszy separator jest napisem pustym.
    */
    List<String> getOperatorsForStringPrefix(String s) {
        List<String> returnedId = new ArrayList<String>();
        String firstSeparator;

        for (OperatorFactory operator : definedOperators) {
            firstSeparator = operator.separators.get(0);

            if (s.startsWith(firstSeparator) && !firstSeparator.equals(""))
                returnedId.add(operator.id);
        }
        if (returnedId.isEmpty()) {
            for (OperatorFactory operator : definedOperators) {
                firstSeparator = operator.separators.get(0);

                if (firstSeparator.equals(""))
                    returnedId.add(operator.id);
            }
        }
        return returnedId;
    }
}
