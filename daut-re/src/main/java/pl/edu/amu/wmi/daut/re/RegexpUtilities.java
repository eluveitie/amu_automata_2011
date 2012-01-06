package pl.edu.amu.wmi.daut.re;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;

/**
 * Klasa posiadająca metodę, która z drzewa operatorów robi automat.
 */
public class RegexpUtilities {

    protected RegexpUtilities() { throw new UnsupportedOperationException(); }

    static AutomatonSpecification createAutopmatonFromOperatorTree(RegexpOperatorTree tree) {

            //przejdź przez drzewo stanów metodą post-order, przy pomocy dwóch stosów.
            Stack<RegexpOperatorTree> child = new Stack<RegexpOperatorTree>();
            Stack<RegexpOperatorTree> parent = new Stack<RegexpOperatorTree>();
            child.push(tree);
            while (!child.empty()) {

                RegexpOperatorTree current = child.peek();
                parent.push(current);
                child.pop();

                for (RegexpOperatorTree subTree : current.getSubtrees())
                    child.push(subTree);
            }

            //na stosie "parent" mamy teraz wierzchołki w porządku post-order!
            //w porządku post-order chodzi o to, że zawsze zaczynamy od nieodwiedzonych liści
            //i idziemy powoli w kierunku korzenia drzewa.

            //utwórz mapę poddrzew na automaty przez nich utworzone.
            Map<RegexpOperatorTree, AutomatonSpecification> map = new HashMap<RegexpOperatorTree,
                                                                       AutomatonSpecification>();

            while (!parent.empty()) {

                RegexpOperatorTree current = parent.peek();

                //utwórz listę automatów utworzonych przez synów wierzchołka.
                List<AutomatonSpecification> arguments = new ArrayList<AutomatonSpecification>();
                for (RegexpOperatorTree subTree : current.getSubtrees()) {

                    //nie będzie tutaj odwołania do nieistniejących kluczy ze
                    //wzgl. na charakter porządku post-order. jeśli wystąpi tutaj
                    //exception, to znaczy, że źle zaimplementowaliśmy coś wcześniej.
                    AutomatonSpecification subTreeAutomaton = map.get(subTree);
                    arguments.add(subTreeAutomaton);
                }

                //utwórz automat, którego argumentami są automaty wszystkich synów.
                AutomatonSpecification currentAutomaton = current.getRoot().createAutomaton(
                                                                                 arguments);
                //zapamiętaj automat dla danego wierzchołka. ponieważ liście się
                //wykonają "najpierw", to nadchodzący po tym rodzice tych liści
                //będą mieli pełną informację o automatach utworzonych przez
                //swoich synów...
                map.put(current, currentAutomaton);

                parent.pop();

                //usunęliśmy właśnie wierzchołek-korzeń - zostaliśmy z pustym stosem,
                //możemy zwrócić automat.
                if (parent.empty())
                    return currentAutomaton;
            }

            //ten kod nie powinien się nigdy wykonać.
            return null;
    }
}
