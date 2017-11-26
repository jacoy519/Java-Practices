import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {

    public static void main(String[] args) {
        final List<RepaymentPlanEntity> lastNotUseRpList = new ArrayList<RepaymentPlanEntity>();

        //page select
        while(true) {
            List<RepaymentPlanEntity> repaymentPlanEntityList = new ArrayList<RepaymentPlanEntity>();

            if(!lastNotUseRpList.isEmpty()) {
                Map<Long, RepaymentPlanEntity> lastNotUseRpMap = transferRpListToRpMap(lastNotUseRpList);
                Map<Long, RepaymentPlanEntity> currentPageRpMap = transferRpListToRpMap(repaymentPlanEntityList);
                List<RepaymentPlanEntity> currentUseRpList = createCurrentUseRpList(lastNotUseRpMap, currentPageRpMap);
                List<RepaymentPlanEntity> currentNotUseRpList = createCurrentNotUsedRpList(lastNotUseRpMap, currentPageRpMap);
                lastNotUseRpList.clear();
                lastNotUseRpList.addAll(currentNotUseRpList);

                //dosomething here use currentUseRpList
            } else {
                lastNotUseRpList.clear();
                lastNotUseRpList.addAll(repaymentPlanEntityList);
            }
        }

        if(!lastNotUseRpList.isEmpty()) {
            //dosomething
        }
    }



    private static List<RepaymentPlanEntity> createCurrentUseRpList( Map<Long, RepaymentPlanEntity> lastNoUseRpMap,Map<Long, RepaymentPlanEntity> currentPageRpMap) {
        Map<Long, RepaymentPlanEntity> currentUseRpMap = new HashMap<>();
        for(Map.Entry<Long, RepaymentPlanEntity> rpEntity : lastNoUseRpMap.entrySet()) {
            if(currentPageRpMap.containsKey(rpEntity.getKey())) {
                RepaymentPlanEntity lastNoUseRp = rpEntity.getValue();
                RepaymentPlanEntity currentPageRp = currentPageRpMap.get(rpEntity.getKey());
                RepaymentPlanEntity targetRp = lastNoUseRp.getPlanNumber() >= currentPageRp.getPlanNumber() ? lastNoUseRp : currentPageRp;
                currentUseRpMap.put(targetRp.getLoanId(), targetRp);
            } else {
                currentUseRpMap.put(rpEntity.getKey(), rpEntity.getValue());
            }
        }
        List<RepaymentPlanEntity> currentUseRpList = new ArrayList<>();
        currentUseRpList.addAll(currentUseRpMap.values());
        return currentUseRpList;
    }

    private static List<RepaymentPlanEntity> createCurrentNotUsedRpList( Map<Long, RepaymentPlanEntity> lastNoUseRpMap,Map<Long, RepaymentPlanEntity> currentPageRpMap) {
        Map<Long, RepaymentPlanEntity> currentNotUseRpMap = new HashMap<>();
        for(Map.Entry<Long, RepaymentPlanEntity> rpEntity : currentPageRpMap.entrySet()) {
            if(!lastNoUseRpMap.containsKey(rpEntity.getKey())) {
                currentNotUseRpMap.put(rpEntity.getKey(), rpEntity.getValue());
            }
        }
        List<RepaymentPlanEntity> currentUseRpList = new ArrayList<>();
        currentUseRpList.addAll(currentNotUseRpMap.values());
        return currentUseRpList;
    }

    private static Map<Long, RepaymentPlanEntity> transferRpListToRpMap(List<RepaymentPlanEntity> rpList) {
        Map<Long, RepaymentPlanEntity> rpMap = new HashMap<>();
        for(RepaymentPlanEntity rp : rpList) {
            if(rpMap.containsKey(rp.getLoanId())) {
                RepaymentPlanEntity currentRp = rpMap.get(rp.getLoanId());
                if(rp.getPlanNumber() > currentRp.getPlanNumber()) {
                    rpMap.put(rp.getLoanId(), rp);
                } else {
                    rpMap.put(rp.getLoanId(), rp);
                }
            }
        }
        return rpMap;
    }

    public static class RepaymentPlanEntity  {

        private Long loanId;

        private Long planNumber;


        public Long getPlanNumber() {
            return planNumber;
        }

        public void setPlanNumber(Long planNumber) {
            this.planNumber = planNumber;
        }

        public Long getLoanId() {
            return loanId;
        }

        public void setLoanId(Long loanId) {
            this.loanId = loanId;
        }
    }
}
