package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.models.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@ComponentScan("config")
public class UserDAOImpl implements UserDAO {
    private static int PEOPLE_COUNT;
    private List<User> people;


    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //Блок инициализации или можно конструктор использовать
    {
        people = new ArrayList<>();

        people.add(new User(++PEOPLE_COUNT, "Tom", "Brady", 4));
        people.add(new User(++PEOPLE_COUNT, "Bob", "Marley", 5));
        people.add(new User(++PEOPLE_COUNT, "Mike", "Jackson", 18));
        people.add(new User(++PEOPLE_COUNT, "Katy", "Perry", 47));
    }


    @Transactional
    public List<User> index() {
        return people;
        //return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    @Transactional
    public User show(int id){
        return people.stream().filter(person -> person.getId()==id).findAny().orElse(null);
        //return entityManager.find(User.class, id);
    }

    @Transactional
    public void save (User user){
        user.setId(++PEOPLE_COUNT);
        people.add(user);
        //entityManager.persist(user);
        //entityManager.close();
    }

    @Transactional
    public void update (int id, User updatePerson) {
        User personToBeUpdate = show(id);
        //User personToBeUpdate =  entityManager.getReference(User.class,id);

        personToBeUpdate.setName(updatePerson.getName());
        personToBeUpdate.setLastName(updatePerson.getLastName());
        personToBeUpdate.setAge(updatePerson.getAge());
    }

    @Transactional
    public void delete (int id) {
        people.removeIf(p->p.getId()==id);
//        User user = entityManager.find(User.class, id);
//        if (user != null) {
//            entityManager.remove(user);
//        }
    }
}
