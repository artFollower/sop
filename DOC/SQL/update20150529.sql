update t_pcs_initial_fee set isFinish=0 where isFinish=null 
update t_pcs_initial_fee set fundsStatus=0,billingStatus=0
update t_pcs_extend_fee set isFinish=0 where isFinish=null 
update t_pcs_extend_fee set fundsStatus=0,billingStatus=0