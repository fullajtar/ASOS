# The example consists of three microservices:

## Customer service:
This service is responsible for managing customer information. It exposes a REST API for creating, updating, and retrieving customer information.

## Order service:
This service is responsible for managing orders. It exposes a REST API for creating, updating, and retrieving orders.

&nbsp;
To handle the REST requests, we need to define the necessary service methods and repository interfaces.

## Customer service interface:

```
import com.example.demo.model.Customer;
import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();
    Customer getCustomer(long id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(long id, Customer customer);
    void deleteCustomer(long id);
}
```

## Customer repository interface:

```
import com.example.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAll();
}
```

## Order service interface:

```
import com.example.demo.model.Order;
import java.util.List;

public interface OrderService {

    public List<Order> getOrders();
    public Order getOrder(long id) ;
    public Order createOrder(Order order) ;
    public Order updateOrder(long id, Order order) ;
    public void deleteOrder(long id) ;
}
```

## Order repository interface:

```
import com.example.demo.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll();
}
```

## Customer service implementation:

```
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(long id, Customer customer) {
        customer.setId(id);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }
}
```

The CustomerServiceImpl class implements the CustomerService interface and injects an instance of the CustomerRepository interface using constructor injection. It provides implementations for the service methods defined in the CustomerService interface. The createCustomer and updateCustomer methods use the save method of the CustomerRepository to create or update a customer in the database. The deleteCustomer method uses the deleteById method of the CustomerRepository to delete a customer from the database.

The getCustomers and getCustomer methods use the findAll and findById methods of the CustomerRepository to retrieve customer information from the database.

## Order service implementation:

```
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(long id, Order order) {
        order.setId(id);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
```

The OrderServiceImpl class is similar to the CustomerServiceImpl class. It implements the OrderService interface and injects an instance of the OrderRepository interface using constructor injection. It provides implementations for the service methods defined in the OrderService interface. The createOrder and updateOrder methods use the save method of the OrderRepository to create or update an order in the database. The deleteOrder method uses the deleteById method of the OrderRepository to delete an order from the database.

The getOrders and getOrder methods use the findAll and findById methods of the OrderRepository to retrieve order information from the database.

## Customer class:

```
import javax.persistence.*;

@Table
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

The Customer class is a simple POJO (Plain Old Java Object) that represents a customer. It has three fields: id, name, and email. The id field is annotated with the @Id and @GeneratedValue annotations to mark it as the primary key of the entity and to configure its generation strategy.

## Order class:

```
import javax.persistence.*;

@Table
@Entity
public class Order {

    public Order() {}

    public Order(long id, long customerId, String product, int quantity) {
        this.id = id;
        this.customerId = customerId;
        this.product = product;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long customerId;
    private String product;
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
```

The Order class is similar to the Customer class. It has four fields: id, customerId, product, and quantity. The id field is annotated with the @Id and @GeneratedValue annotations to mark it as the primary key of the entity and to configure its generation strategy. The customerId field represents the ID of the customer that placed the order.

## Customer controller:

```
import com.example.demo.model.Customer;
import com.example.demo.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
    }
}
```

The CustomerController class is a REST controller that exposes the REST endpoints for the Customer service. It uses the @RestController annotation to mark it as a controller and the @RequestMapping annotation to specify the base URL for the REST endpoints. It injects an instance of the CustomerService interface using constructor injection.

It defines the following methods to handle the REST requests:

```
getCustomers: handles the GET /customers request and returns a list of customers
getCustomer: handles the GET /customers/{id} request and returns a customer by ID
createCustomer: handles the POST /customers request and creates a new customer
updateCustomer: handles the PUT /customers/{id} request and updates a customer by ID
deleteCustomer: handles the DELETE /customers/{id} request and deletes a customer by ID
```

Each method invokes the corresponding method of the CustomerService to perform the operation and returns the result.

## Order controller:

```
import com.example.demo.model.Order;
import com.example.demo.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable long id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
    }
}
```

The OrderController class is similar to the CustomerController class. It is a REST controller that exposes the REST endpoints for the Order service. It uses the @RestController annotation to mark it as a controller and the @RequestMapping annotation to specify the base URL for the REST endpoints. It injects an instance of the OrderService interface using constructor injection.

It defines the following methods to handle the REST requests:

    getOrders: handles the GET /orders request and returns a list of orders
    getOrder: handles the GET /orders/{id} request and returns an order by ID
    createOrder: handles the POST /orders request and creates a new order
    updateOrder: handles the PUT /orders/{id} request and updates an order by ID
    deleteOrder: handles the DELETE /orders/{id} request and deletes an order by ID

Each method invokes the corresponding method of the OrderService to perform the operation and returns the result.
