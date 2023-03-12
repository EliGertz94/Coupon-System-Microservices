package com.coupons.couponsystem.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class PurchasesListDTO extends ResponseMessage{

    private  List<PurchaseDTO> purchasesDTO;

//    public PurchasesListDTO(String message, List<PurchaseDTO> purchasesDTO) {
//        super(message);
////        List<PurchaseDTO> purchesList=  new ArrayList<>();
////            for (PurchaseDTO purchaseDTO:
////            purchasesDTO){
////                purchesList.add(purchaseDTO);
////        }
//
//            if(this.purchasesDTO!=null){
//
//                this.purchasesDTO=purchasesDTO;
//            }else {
//                this.purchasesDTO= new ArrayList<>();
//                this.purchasesDTO=purchasesDTO;
//            }
//
//    }


    public PurchasesListDTO(String message,List<PurchaseDTO> purchasesDTO) {
        super(message);
        this.purchasesDTO = purchasesDTO;
    }

    public List<PurchaseDTO> getPurchasesDTO() {
        return purchasesDTO;
    }

    public void setPurchasesDTO(List<PurchaseDTO> purchasesDTO) {
        this.purchasesDTO = purchasesDTO;
    }
}
