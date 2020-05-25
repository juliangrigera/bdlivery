package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryMongoRepository;
import org.bson.types.ObjectId;


public class DBliveryServiceImpl implements  DBliveryService {
    private DBliveryMongoRepository repository;

    DBliveryServiceImpl(DBliveryMongoRepository repository) {
        super();
        this.repository = repository;
    }

    @java.lang.Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
        return null;
    }

    @java.lang.Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
        return null;
    }

    @java.lang.Override
    public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        return null;
    }

    @java.lang.Override
    public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        return null;
    }

    @java.lang.Override
    public Product updateProductPrice(ObjectId id, Float price, Date startDate) throws DBliveryException {
        return null;
    }

    @java.lang.Override
    public Optional<User> getUserById(ObjectId id) {
        return null;
    }

    @java.lang.Override
    public Optional<User> getUserByEmail(String email) {
        return null;
    }

    @java.lang.Override
    public Optional<User> getUserByUsername(String username) {
        return null;
    }

    @java.lang.Override
    public Optional<Order> getOrderById(ObjectId id) {
        return null;
    }

    @java.lang.Override
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
        return null;
    }

    @java.lang.Override
    public Order addProduct(ObjectId order, Long quantity, Product product) throws DBliveryException {
        return null;
    }

    @java.lang.Override
    public Order deliverOrder(ObjectId order, User deliveryUser) throws DBliveryException {
        return null;
    }

    @java.lang.Override
    public Order deliverOrder(ObjectId order, User deliveryUser, Date date) throws DBliveryException {
        return null;
    }

    @java.lang.Override
    public Order cancelOrder(ObjectId order) throws DBliveryException {
        return null;
    }

    @java.lang.Override
    public Order cancelOrder(ObjectId order, Date date) throws DBliveryException {
        return null;
    }

    @java.lang.Override
    public Order finishOrder(ObjectId order) throws DBliveryException {
        return null;
    }

    @java.lang.Override
    public Order finishOrder(ObjectId order, Date date) throws DBliveryException {
        return null;
    }

    @java.lang.Override
    public boolean canCancel(ObjectId order) throws DBliveryException {
        return false;
    }

    @java.lang.Override
    public boolean canFinish(ObjectId id) throws DBliveryException {
        return false;
    }

    @java.lang.Override
    public boolean canDeliver(ObjectId order) throws DBliveryException {
        return false;
    }

    @java.lang.Override
    public OrderStatus getActualStatus(ObjectId order) {
        return null;
    }

    @java.lang.Override
    public List<Product> getProductsByName(String name) {
        return null;
    }
}
