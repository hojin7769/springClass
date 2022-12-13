package di;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("service")
public class RecordViewImpl implements RecordView {

	// 1. 결합성 높은 코딩 x
	//private RecordImpl record = new RecordImpl();
	@Autowired
	private RecordImpl record = null;
	
	//getter,setter
	public RecordImpl getRecord() {
		return record;
	}
	//setter 를 사용해서 record 객체 참조
	public void setRecord(RecordImpl record) {
		this.record = record;
	}
	
	//생성자
	public RecordViewImpl() {
		super();
	}
	//생성자를 사용해서 record 객체 참조(주입)
	public RecordViewImpl(RecordImpl record) {
		super();
		this.record = record;
	}
	
	
	@Override
	public void input() {
		
		try (Scanner scanner = new Scanner(System.in)){
			
			System.out.print("> kor , eng , mat input?");
			this.record.setKor( scanner.nextInt() );
			this.record.setEng( scanner.nextInt() );
			this.record.setMat( scanner.nextInt() );
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void output() {
		
		System.out.printf(">kor :%d , eng:%d , mat:%d , tot=%d , avg = %.2f\n"
				,this.record.getKor()
				,this.record.getEng()
				,this.record.getMat()
				,this.record.total()
				,this.record.avg()
				);
		
		
	}

}
