package model;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @Column(length = 20)
    private String id;

    @Column(name = "room_number", length = 20)
    private String roomNumber;

    @Column(name = "student_id", length = 20)
    private String studentId;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "monthly_fee", nullable = false)
    private double monthlyFee;

    // Quan hệ với Room
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_number", referencedColumnName = "room_number",
                insertable = false, updatable = false)
    private Room room;

    // Quan hệ với Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id",
                insertable = false, updatable = false)
    private Student student;

    public Contract() { }

    public Contract(String id, String roomNumber, String studentId,
                    Date startDate, Date endDate, double monthlyFee) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.studentId = studentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyFee = monthlyFee;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
