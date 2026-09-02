package ProcureFlow.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "budgets",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"department_id", "year"}
                )
        }
)
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal usedAmount = BigDecimal.ZERO;

    public Budget() {
    }

    public Budget(
            Department department,
            Integer year,
            BigDecimal amount
    ) {
        this.department = department;
        this.year = year;
        this.amount = amount;
        this.usedAmount = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public Integer getYear() {
        return year;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }
}