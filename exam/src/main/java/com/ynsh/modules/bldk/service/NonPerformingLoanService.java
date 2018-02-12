package com.ynsh.modules.bldk.service;

import com.ynsh.common.config.Global;
import com.ynsh.common.core.persistence.Page;
import com.ynsh.common.core.service.CrudService;
import com.ynsh.modules.bldk.dao.NonPerformingLoanDao;
import com.ynsh.modules.bldk.entity.DailyPrincipalInterest;
import com.ynsh.modules.bldk.entity.NonPerformingLoan;
import com.ynsh.modules.bldk.entity.RepaymentRecord;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ynsh.modules.sys.utils.DataScopeUtils.dataScopeFilter;

/**
 * Created by syb on 18-1-8.
 */
@Service
@Transactional(readOnly = true)
public class NonPerformingLoanService extends CrudService<NonPerformingLoanDao,NonPerformingLoan,Long>{

    @Autowired
    private RepaymentRecordService repaymentRecordService;

    public Page<NonPerformingLoan> findPage(Page<NonPerformingLoan> page, NonPerformingLoan entity) {
        UserOrganization userOrganization = UserUtils.getUserOrganization();
        entity.getSqlMap().put("dsf",dataScopeFilter(Global.getBldkModuleId(), userOrganization, "o", ""));

        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    /**
     * 获取下一个结息日
     * @param currentDate 当前日期
     * @param interestTerm 结息类型
     */
    private Date getNextComputeInterestDate(Date currentDate, String interestTerm) throws Exception{

        if (!interestTerm.equals("0") && interestTerm.equals("1") && interestTerm.equals("2")) throw new Exception("无法识别的计息周期");
        //确保只有日期没有时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf.parse(sdf.format(currentDate));
        // 创建 Calendar 对象
        Calendar calendarCurrentDate = Calendar.getInstance();
        calendarCurrentDate.setTime(currentDate);
        int year = calendarCurrentDate.get(Calendar.YEAR);
        int month = calendarCurrentDate.get(Calendar.MONTH) + 1;//从0开始，所以+1
        int day = calendarCurrentDate.get(Calendar.DAY_OF_MONTH);

        //按月计息
        if (interestTerm.equals("0")){
            if (day > 20) {
                calendarCurrentDate.add(Calendar.MONTH,1);
                calendarCurrentDate.set(Calendar.DAY_OF_MONTH,20);
            }
            else calendarCurrentDate.set(Calendar.DAY_OF_MONTH,20);

            return calendarCurrentDate.getTime();
        }

        //按季计息
        if (interestTerm.equals("1")){
            if (month <= 3 && day <=20) month = 3;
            else if (month < 3 && day >20) month=3;
            else if (month == 3 && day >20) month=6;
            else if (month>3 && month <=6 && day <= 20) month = 6;
            else if (month>3 && month <6 && day > 20) month = 6;
            else if (month>3 && month ==6 && day >20) month=9;
            else if (month>6 && month <=9 && day <= 20) month = 9;
            else if (month>6 && month <9 && day > 20) month = 9;
            else if (month>6 && month ==9 && day > 20) month = 12;
            else if (month>9 && month <=12 && day <= 20) month = 12;
            else if (month>9 && month <12 && day > 20) month = 12;
            else if (month>9 && month ==12 && day >20) {
                year = year + 1;
                month = 3;
            }
            else throw new Exception("错误的月份");

            calendarCurrentDate.set(Calendar.YEAR, year);
            calendarCurrentDate.set(Calendar.MONTH, month-1);
            calendarCurrentDate.set(Calendar.DAY_OF_MONTH, 20);
            return calendarCurrentDate.getTime();
        }

        //按年计息
        if (interestTerm.equals("2")){
            if (month==12 && day>20){
                calendarCurrentDate.add(Calendar.YEAR,1);
                calendarCurrentDate.set(Calendar.DAY_OF_MONTH,20);
            }
            else {
                calendarCurrentDate.set(Calendar.MONTH,12-1);
                calendarCurrentDate.set(Calendar.DAY_OF_MONTH,20);
            }
            return calendarCurrentDate.getTime();
        }

        return null;

    }

    /**
     * 计算从开始日到结束日每日的本金、利息、复利
     * @param endDate 结束日，当日未产生利息，如计息截止日是2018-01-17,则实际最后一次产生利息的日期是：2018-01-16，此处应输入2018-01-17
     * @param currentLoan 当前不良贷款
     * @param repaymentRecords 当前不良贷款的还款记录
     */
    public DailyPrincipalInterest getDailyPrincipalInterest(Date endDate, NonPerformingLoan currentLoan, List<RepaymentRecord> repaymentRecords) throws Exception{
        //确保只有日期没有时间
        Date beginDate = currentLoan.getHxDate();
        BigDecimal fxRate = currentLoan.getFxRate();
        beginDate = getOnlyDate(beginDate);
        endDate = getOnlyDate(endDate);
        if (!endDate.after(beginDate)) throw new Exception("计算利息日期小于或等于核销日期");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        DailyPrincipalInterest dailyPrincipalInterest = new DailyPrincipalInterest();
        dailyPrincipalInterest.setPrincipal(currentLoan.getPrincipal());
        dailyPrincipalInterest.setInterest(currentLoan.getInterest());
        dailyPrincipalInterest.setCompoundInterest(currentLoan.getCompoundInterest());
        BigDecimal lastTermInterest = currentLoan.getBeforeHxInterest();
        String interestTerm = currentLoan.getInterestTerm();
        DailyPrincipalInterest singleDayPrincipalInterest = new DailyPrincipalInterest();

        while (calendar.getTime().before(endDate)){
            singleDayPrincipalInterest = getSingleDayPrincipalInterest(calendar.getTime(),dailyPrincipalInterest,repaymentRecords, lastTermInterest,fxRate);
            if (getNextComputeInterestDate(calendar.getTime(),interestTerm).equals(calendar.getTime())){
                lastTermInterest =singleDayPrincipalInterest.getInterest();
            }
            calendar.add(Calendar.DATE,1);
            dailyPrincipalInterest = singleDayPrincipalInterest;
        }

        return singleDayPrincipalInterest;
    }

    /**
     * 根据还款记录与昨日的贷款余额计算当日的贷款余额
     * @param date 要计算贷款余额的日期
     * @param initPrincipalInterest 初始贷款余额，即昨日贷款余额
     * @param repaymentRecords 还款记录
     * @param lastTermInterest 上期利息，用作计算复利的基数
     */
    private DailyPrincipalInterest getSingleDayPrincipalInterest(Date date, DailyPrincipalInterest initPrincipalInterest, List<RepaymentRecord> repaymentRecords, BigDecimal lastTermInterest, BigDecimal fxRate) throws Exception{
//        if (repaymentRecords.size() == 0) return initPrincipalInterest;

        BigDecimal dayRate = fxRate.divide(new BigDecimal(30000),8,BigDecimal.ROUND_HALF_UP);
        Date currentDate = getOnlyDate(date);

        //处理还款
        for (int i=0;i<repaymentRecords.size();i++){
            RepaymentRecord currentRepaymentRecord = repaymentRecords.get(i);
            Date recordDate = getOnlyDate(currentRepaymentRecord.getRepaymentDate());
            if (currentDate.equals(recordDate)){
                initPrincipalInterest.setPrincipal(initPrincipalInterest.getPrincipal().subtract(currentRepaymentRecord.getRepaymentPrincipal()));
                initPrincipalInterest.setInterest(initPrincipalInterest.getInterest().subtract(currentRepaymentRecord.getRepaymentInterest()));
                initPrincipalInterest.setCompoundInterest(initPrincipalInterest.getCompoundInterest().subtract(currentRepaymentRecord.getRepaymentCompoundInterest()));
            }
        }

        //根据currentDate之前一天的记录生成当日的初始利息、复利，本金无需生成（本金的改变只有还款）
        initPrincipalInterest.setInterest(initPrincipalInterest.getInterest().add(initPrincipalInterest.getPrincipal().multiply(dayRate))); //原始利息+当日利息
        initPrincipalInterest.setCompoundInterest(initPrincipalInterest.getCompoundInterest().add(lastTermInterest.multiply(dayRate))); //原始复利+当日复利
        initPrincipalInterest.setDate(currentDate);
        return initPrincipalInterest;
    }

    /**
     * 返回日期时间
     * 如时间 2017-01-01 21:15:13 返回 2017-01-01 00:00:00
     * @param date
     */
    private Date getOnlyDate(Date date) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(date));
    }


}
