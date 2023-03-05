package com.coupons.couponsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class CouponSystemApplication {

	public static Logger logger = LoggerFactory.getLogger(CouponSystemApplication.class);
	public static void main(String[] args) {

		SpringApplication.run(CouponSystemApplication.class);

//		String list = "avi,yona ,yuval,anton,yakir";
////
////		List<String> names =new  ArrayList<>(Arrays.stream(
////				list.split(",")).map((e)->e.replaceAll("\\s","")).toList());
////
////	names.forEach(System.out::println);


		//
//		LoginManager loginManager = LoginManager.getInstance();
//
//		AdminService admin = (AdminService) loginManager.logIn
//				("admin@admin.com","admin",ClientType.Administrator);
//
//		Company company1 = new Company(0L,"aaa","aaa@aaa.com","aaa123",null);
//		Company company2 = new Company(0L,"bbb","bbb@bbb.com","bbb123",null);
//
//		//admin.addCompany(company1);
//		//admin.addCompany(company2);
//
//		CompanyService company = (CompanyService)
////			loginManager.logIn("aaa@aaa.com", "aaa123", ClientType.Company);
////
//
////			company.addCoupon(new Coupon(0L,null,Category.Restaurant,"new second coupon","description",
////							LocalDateTime.now(),LocalDateTime.of(2022, 11, 11, 12, 12)
////							,150,120,"image"));
////		company.addCoupon(new Coupon(0L,null,Category.Restaurant,"new therd coupon","description",
////				LocalDateTime.now(),LocalDateTime.of(2022, 11, 11, 12, 12)
////				,150,120,"image"));
////		company.addCoupon(new Coupon(0L,null,Category.Restaurant,"new forth coupon","description",
////				LocalDateTime.now(),LocalDateTime.of(2022, 11, 11, 12, 12)
////				,150,120,"image"));
//			company.updateCoupon(new Coupon(1L,null,Category.FOOD,"food first coupon","description",
//					LocalDateTime.now(),LocalDateTime.of(2022, 11, 11, 12, 12)
//					,150,120,"image"));
//
//		//	company.deleteCoupon(3L);
//
//
//	List<Coupon> coupons= company.getAllCompanyCoupons();
//		List<Coupon> couponsByCategory = company.getAllCompanyCouponsByCategory(Category.FOOD);
//		System.out.println("all company coupons");
//		printList(coupons);
//		System.out.println("coupons by category ");
//		printList(couponsByCategory);
//
//		//List<Coupon> byPrice= company.getAllCompanyCouponsByPrice(102);


	}
//
//
//		//
//
////		CompanyServiceImpl companyService = ctx.getBean(CompanyServiceImpl.class);
////		companyService.logIn("aaa@gmail.com","aaacombbpbany");
////		logger.info("copany coupons  {}",companyService.getAllCompanyCoupons());
//		LoginManager loginManager =LoginManager.getInstance();
//		LoginManager loginManager2 =LoginManager.getInstance();
//		//LoginManager loginManager2 = LoginManager.getInstance();
//		logger.info("login objects -  {} {}",loginManager,loginManager2);
//
//
//
//


//
//		AdminServiceImpl admin = (AdminServiceImpl)
//					loginManager.logIn("admin@admin.com", "admin", ClientType.Administrator);
//
//
//		CustomerService customer = (CustomerService)
//					loginManager.logIn("lolaa@gmaul.comd", "eli1234", ClientType.Customer);
//
//	}



}
