package com.bw.petclinic.owner.repository;

import com.bw.petclinic.owner.domain.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OwnerRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    public void setup() {
        System.out.println("Running setup");
        jdbcTemplate.batchUpdate(
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');",
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');",
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');",
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');",
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');",
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');",
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');",
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');",
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');",
                "INSERT INTO owners (first_name,last_name,address,city,telephone) " +
                        "VALUES ('Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');");
    }

    @AfterEach
    public void teardown() {
        System.out.println("Running teardown");
        jdbcTemplate.update("delete from owners");
    }

    @Test
    public void testSave() {
        Owner owner = new Owner(1, "Jason", "Tan",
                "1 Martin Place", "Sydney", "0123456789");
        owner.setId(0);
        Owner savedOwner = ownerRepository.save(owner);
        assertEquals("Jason", savedOwner.getFirstName());
    }

    @Test
    public void testFindById() {
        List<Owner> owners = ownerRepository.findAll();
        Optional<Owner> owner = ownerRepository.findById(owners.get(0).getId());
        assertTrue(owner.isPresent());
        assertEquals(owners.get(0).getId(), owner.get().getId());
    }

    @Test
    public void testFindAll() {
        List<Owner> owners = ownerRepository.findAll();
        assertFalse(owners.isEmpty());
        assertEquals(10, owners.size());
    }

    @Test
    public void testFindPageByLastName() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Owner> owners = ownerRepository.findPageByLastName("Davis", pageable);
        assertFalse(owners.isEmpty());
        assertEquals(2, owners.getContent().size());
    }

}
