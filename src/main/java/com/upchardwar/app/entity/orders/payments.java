package com.upchardwar.app.entity.orders;

import com.upchardwar.app.entity.Role;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.paymentstatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class payments {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    
    @ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

    private Long receiverId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private orderDetail orderDetails;

    
    private paymentstatus paymentStatus;
}
