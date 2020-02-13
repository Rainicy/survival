package data_formatter.brfss;

import cox_hazards.DataFormat;
import cox_hazards.MultiKey;
import edu.neu.ccs.pyramid.util.Pair;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.io.*;
import java.util.*;

/**
 * Created by Rainicy on 9/29/17
 */
public class DSFormat {

    public static DataFormat loadO3(String input, String group) throws IOException {
        switch (group) {
            case "default_o3cms_asr2008": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0, 5, 6, 7});
            case "o3only_o3cms_asr2008": return loadO3CMS_ASR2008(input, new int[]{9}, new int[]{0, 5, 6, 7});
            case "o3only_nostate_o3cms_asr2008": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{0, 5, 6, 7}, 16);
            case "default_nostate_o3cms_asr2008_all": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 16);
            case "default_nostate_o3cms_asr2008_nonac": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 17);
            case "default_nostate_o3cms_asr2008_cvd": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 19);
            case "default_nostate_o3cms_asr2008_ihd": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 20);
            case "default_nostate_o3cms_asr2008_chf": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 21);
            case "default_nostate_o3cms_asr2008_cbv": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 22);
            case "default_nostate_o3cms_asr2008_resp": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 23);
            case "default_nostate_o3cms_asr2008_copd": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 24);
            case "default_nostate_o3cms_asr2008_pneu": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 25);
            case "default_nostate_o3cms_asr2008_canc": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 26);
            case "default_nostate_o3cms_asr2008_lungc": return loadO3CMS_ASR2008_NoState(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 27);

            case "o3_age": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{5}, 16);
            case "o3_sex": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{6}, 16);
            case "o3_race": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{7}, 16);

            case "o3_state_age": return loadO3CMS_ASR2008(input, new int[]{9}, new int[]{5}, 16, 15);
            case "o3_state_sex": return loadO3CMS_ASR2008(input, new int[]{9}, new int[]{6}, 16, 15);
            case "o3_state_race": return loadO3CMS_ASR2008(input, new int[]{9}, new int[]{7}, 16, 15);

            case "o3_state_pm_age": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{5}, 16, 15);
            case "o3_state_pm_sex": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{6}, 16, 15);
            case "o3_state_pm_race": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{7}, 16, 15);



            case "o3_only_all": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{5,6,7}, 16);
            case "o3_pm_all": return loadO3CMS_ASR2008_NoState(input, new int[]{9,28}, new int[]{5,6,7}, 16);
            case "o3_pm": return loadO3CMS_ASR2008_NoState(input, new int[]{9,28}, new int[]{}, 16);
            case "o38h_only_all": return loadO3CMS_ASR2008_NoState(input, new int[]{10}, new int[]{5,6,7}, 16);
            case "o38h_pm_all": return loadO3CMS_ASR2008_NoState(input, new int[]{10,28}, new int[]{5,6,7}, 16);
            case "o38h_pm": return loadO3CMS_ASR2008_NoState(input, new int[]{10,28}, new int[]{}, 16);

            case "o3_only_all_state": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{1,5,6,7}, 16);
            case "o3_pm_all_state": return loadO3CMS_ASR2008_NoState(input, new int[]{9,28}, new int[]{1,5,6,7}, 16);
            case "o38h_only_all_state": return loadO3CMS_ASR2008_NoState(input, new int[]{10}, new int[]{1,5,6,7}, 16);
            case "o38h_pm_all_state": return loadO3CMS_ASR2008_NoState(input, new int[]{10,28}, new int[]{1,5,6,7}, 16);


            case "o3_cvd": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{}, 19);
            case "o3_resp": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{}, 23);
            case "o3_lungc": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{}, 27);

            case "o3_siteid": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{0}, 16);
            case "o3_cvd_siteid": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{0}, 19);
            case "o3_resp_siteid": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{0}, 23);
            case "o3_lungc_siteid": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{0}, 27);


            case "o3_other": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{5,6,7}, 16);
            case "o3_all": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{0,5,6,7}, 16);
            case "o3_cvd_all": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{0,5,6,7}, 19);
            case "o3_resp_all": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{0,5,6,7}, 23);
            case "o3_lungc_all": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{0,5,6,7}, 27);

            case "o3_pm_resp_all": return loadO3CMS_ASR2008_NoState(input, new int[]{9, 28}, new int[]{0,5,6,7}, 23);
            case "o3_pm_resp_state": return loadO3CMS_ASR2008_NoState(input, new int[]{9, 28}, new int[]{1,5,6,7}, 23);
            case "o3_pm_resp_all_state": return loadO3CMS_ASR2008_NoState(input, new int[]{9, 28}, new int[]{0,1,5,6,7}, 23);

            case "o38h_all": return loadO3CMS_ASR2008_NoState(input, new int[]{10}, new int[]{0,5,6,7}, 16);


            case "o3_state": return loadO3CMS_ASR2008(input, new int[]{9}, new int[]{}, 16, 15);
            case "o3_state_siteid": return loadO3CMS_ASR2008(input, new int[]{9}, new int[]{0}, 16, 15);
            case "o3_state_other": return loadO3CMS_ASR2008(input, new int[]{9}, new int[]{5,6,7}, 16,15);
            case "o3_state_all": return loadO3CMS_ASR2008(input, new int[]{9}, new int[]{0,5,6,7}, 16, 15);

            case "o38h_state_all": return loadO3CMS_ASR2008(input, new int[]{10}, new int[]{0,5,6,7}, 16, 15);

            case "o3_state_pm": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{}, 16, 15);
            case "o3_state_pm_acc": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{}, 18, 15);
            case "o3_state_pm_cvd": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{}, 19, 15);
            case "o3_state_pm_resp": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{}, 23, 15);
            case "o3_state_pm_canc": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{}, 26, 15);
            case "o3_state_pm_lungc": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{}, 27, 15);

            case "o3_state_pm_siteid": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0}, 16, 15);
            case "o3_state_pm_siteid_acc": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0}, 18, 15);
            case "o3_state_pm_siteid_cvd": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0}, 19, 15);
            case "o3_state_pm_siteid_resp": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0}, 23, 15);
            case "o3_state_pm_siteid_canc": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0}, 26, 15);
            case "o3_state_pm_siteid_lungc": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0}, 27, 15);

            case "o3_state_pm_other": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{5,6,7}, 16, 15);

            case "o3_state_pm_all": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0,5,6,7}, 16, 15);
            case "o3_state_pm_all_acc": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0,5,6,7}, 18, 15);
            case "o3_state_pm_all_cvd": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0,5,6,7}, 19, 15);
            case "o3_state_pm_all_resp": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0,5,6,7}, 23, 15);
            case "o3_state_pm_all_canc": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0,5,6,7}, 26, 15);
            case "o3_state_pm_all_lungc": return loadO3CMS_ASR2008(input, new int[]{9, 28}, new int[]{0,5,6,7}, 27, 15);

            case "o38h_state_pm_all": return loadO3CMS_ASR2008(input, new int[]{10, 28}, new int[]{0,5,6,7}, 16, 15);

            case "no2_state": return loadO3CMS_ASR2008(input, new int[]{3}, new int[]{}, 14, 13);
            case "no2_state_site": return loadO3CMS_ASR2008(input, new int[]{3}, new int[]{0}, 14, 13);
            case "no2_state_other": return loadO3CMS_ASR2008(input, new int[]{3}, new int[]{8, 9, 10}, 14, 13);
            case "no2_state_all": return loadO3CMS_ASR2008(input, new int[]{3}, new int[]{8, 9, 10}, 14, 13);

            case "resp_no2_state": return loadO3CMS_ASR2008(input, new int[]{3}, new int[]{}, 21, 13);
            case "resp_no2_state_site": return loadO3CMS_ASR2008(input, new int[]{3}, new int[]{0}, 21, 13);
            case "resp_no2_state_other": return loadO3CMS_ASR2008(input, new int[]{3}, new int[]{8, 9, 10}, 21, 13);
            case "resp_no2_state_all": return loadO3CMS_ASR2008(input, new int[]{3}, new int[]{8, 9, 10}, 21, 13);






            case "pm_ozone_all": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_nonac": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_cvd": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ihd": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_chf": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_cbv": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_resp": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_copd": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_pneu": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_cancer": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_lungc": return loadO3CMS_ASR2008_NoState(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);

            case "pm_no_ozone_all": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_nonac": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_cvd": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ihd": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_chf": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_cbv": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_resp": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_copd": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_pneu": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_cancer": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_lungc": return loadO3CMS_ASR2008_NoState(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);



            case "ses_nostate_o3cms_asr2008_all": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 16);
            case "ses_nostate_o3cms_asr2008_nonac": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 17);
            case "ses_nostate_o3cms_asr2008_cvd": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 19);
            case "ses_nostate_o3cms_asr2008_ihd": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 20);
            case "ses_nostate_o3cms_asr2008_chf": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 21);
            case "ses_nostate_o3cms_asr2008_cbv": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 22);
            case "ses_nostate_o3cms_asr2008_resp": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 23);
            case "ses_nostate_o3cms_asr2008_copd": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 24);
            case "ses_nostate_o3cms_asr2008_pneu": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 25);
            case "ses_nostate_o3cms_asr2008_canc": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 26);
            case "ses_nostate_o3cms_asr2008_lungc": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{28, 9}, new int[]{0, 5, 6, 7}, 27);

            case "pm_ozone_ses_all": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_nonac": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_cvd": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_ihd": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_chf": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_cbv": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_resp": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_copd": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_pneu": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_cancer": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_ozone_ses_lungc": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7, 14}, new int[]{0, 2, 3, 4}, 6, 5);


            case "pm_no_ozone_ses_all": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_nonac": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_cvd": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_ihd": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_chf": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_cbv": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_resp": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_copd": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_pneu": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_cancer": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);
            case "pm_no_ozone_ses_lungc": return loadO3CMS_ASR2008_NoState_SES(input, new int[]{7}, new int[]{0, 2, 3, 4}, 6, 5);

            case "o3only_nostate_nosite_o3cms_asr2008": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{5, 6, 7}, 16);
            case "o3only_nostrata_nosite_o3cms_asr2008": return loadO3CMS_ASR2008_NoState(input, new int[]{9}, new int[]{}, 16);
            case "o3c": return loadO3Center(input, 22);
            case "o3c_loc": return loadO3CenterLoc(input, 22);
            // o3c_loc_only
            case "o3c_loc_only": return loadO3CenterLocOnly(input, 22);
            case "o3maxh_strata_state": return loadO3CenterState(input, 9);
            case "o3maxh_strata_state_male": return loadO3CenterState(input, 9, 6, "M");
            case "o3maxh_strata_state_female": return loadO3CenterState(input, 9, 6, "F");
            case "o3maxh_strata_state_white": return loadO3CenterState(input, 9, 7, "W");
            case "o3maxh_strata_state_nonwhite": return loadO3CenterState(input, 9, 7, "NW");
            case "o3maxh_state": return loadO3State(input, 9, 8); // use state as features
            case "o3max8h_strata_state": return loadO3CenterState(input, 10);
            case "o3maxh_cent_strata_state": return loadO3CenterState(input, 30);
            case "o3max8h_cent_strata_state": return loadO3CenterState(input, 33);
            case "o3c8h_loc_only": return loadO3CenterLocOnly8h(input, 8);
            case "o3c8h_resp": return loadO3CenterCause8h(input, 8, 13);
            case "o3over_loc_only": return loadO3CenterLocOnly8h(input, 25);
            case "o3over_resp": return loadO3CenterCause8h(input, 25, 13);
            //o3max8h o3maxh
            case "o3max8h": return loadO3Max8h(input, 8, 12, 11, 7);
            case "o3maxh": return loadO3Max8h(input, 9, 12, 11, 7);
            case "o3max8h_1": return loadO3Max8h(input, 28, 12, 11, 7);
            case "o3max8h_resp": return loadO3Max8h(input, 8, 19, 11, 7);
            case "o3max8h_1_resp": return loadO3Max8h(input, 28, 19, 11, 7);
            case "non_o3max8h": return loadO3Max8h(input, 8, 12, 11, 7);
            case "non_o3maxh": return loadO3Max8h(input, 9, 12, 11, 7);

            case "non_o3maxh_state": return loadO3Max8hState(input, 9, 16, 15, 8);
            case "o3maxh_strata_state_new": return loadO3Max8hState(input, 9, 16, 15, 8);
            case "o3maxh_non_strata_state_new": return loadO3Max8hState1(input, 9, 16, 15, 8);
            case "non_o3maxh_state_cvd": return loadO3Max8hState(input, 9, 19, 15, 8);
            case "non_o3maxh_state_resp": return loadO3Max8hState(input, 9, 23, 15, 8);
            case "non_o3maxh_state_canc": return loadO3Max8hState(input, 9, 26, 15, 8);
            case "non_o3maxh_state_lungc": return loadO3Max8hState(input, 9, 27, 15, 8);

            case "non_o3max8h_state": return loadO3Max8hState(input, 10, 16, 15, 8);
            case "non_o3max8h_state_cvd": return loadO3Max8hState(input, 10, 19, 15, 8);
            case "non_o3max8h_state_resp": return loadO3Max8hState(input, 10, 23, 15, 8);
            case "non_o3max8h_state_canc": return loadO3Max8hState(input, 10, 26, 15, 8);
            case "non_o3max8h_state_lungc": return loadO3Max8hState(input, 10, 27, 15, 8);



            case "o3c_cvd": return loadO3CenterCause(input, 22, 9);
            case "o3c_resp": return loadO3CenterCause(input, 22, 13);

            case "o3c_zip": return loadO3CenterByMonitor(input, 22, "/scratch/wang.bin/health/DSenrollee/original/no2ziplist.csv");
            default: throw new RuntimeException("group: " + group + " is not supported yet!");
        }
    }

    private static DataFormat loadO3Max8hState1(String input, int featureIndex, int deathIndex, int totalIndex, int strataIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String ID = lineInfo[strataIndex];
//            String loc = lineInfo[1]; // using state as strata

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[totalIndex]);
            label[1] = Integer.parseInt(lineInfo[deathIndex]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(ID);
//            key.setAge(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3Max8hState(String input, int featureIndex, int deathIndex, int totalIndex, int strataIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String ID = lineInfo[strataIndex];
            String loc = lineInfo[1]; // using state as strata

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[totalIndex]);
            label[1] = Integer.parseInt(lineInfo[deathIndex]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setAge(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3Max8h(String input, int featureIndex, int deathIndex, int totalIndex, int strataIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String ID = lineInfo[strataIndex];
            String loc = lineInfo[0];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[totalIndex]);
            label[1] = Integer.parseInt(lineInfo[deathIndex]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setAge(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadO3CenterCause8h(String input, int featureIndex, int deathIndex) throws IOException {
        DataFormat data = new DataFormat();
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String ID = lineInfo[7];
            String loc = lineInfo[0];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[11]);
            label[1] = Integer.parseInt(lineInfo[deathIndex]);
            if (label[0] == 0) {
                continue;
            }
            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setAge(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        return data;
    }
    private static DataFormat loadO3CenterCause(String input, int featureIndex, int deathIndex) throws IOException {
        DataFormat data = new DataFormat();
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String ID = lineInfo[4];
            String loc = lineInfo[0];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[deathIndex]);
            if (label[0] == 0) {
                continue;
            }
            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setAge(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        return data;
    }

    private static DataFormat loadO3CenterLocOnly8h(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
//            int year = Integer.parseInt(lineInfo[1]);
//            int month = Integer.parseInt(lineInfo[2]);
//            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[7];
//            String loc = lineInfo[0];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[11]);
            label[1] = Integer.parseInt(lineInfo[12]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(ID);
//            key.setAge(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadO3State(String input, int featureIndex, int strataIndex) throws IOException {
        DataFormat data = new DataFormat();
        Map<String, Integer> states = new HashMap<>(62); // 63 states

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String strata = lineInfo[strataIndex];
            String state = lineInfo[1];
            if (!states.containsKey(state)) {
                states.put(state, states.size());
            }

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(63);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1 + states.get(state), 1);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[16]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(63);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadO3CenterState(String input, int featureIndex, int strataIndex, String strataValue) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String gender = lineInfo[strataIndex];
            if (!gender.equals(strataValue)) {
                continue;
            }
            String ID = lineInfo[8];
            String state = lineInfo[1];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[16]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(state);
            key.setAge(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3CenterState(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
//            int year = Integer.parseInt(lineInfo[1]);
//            int month = Integer.parseInt(lineInfo[2]);
//            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[8];
            String state = lineInfo[1];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[16]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(state);
            key.setAge(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3CenterLocOnly(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
//            int year = Integer.parseInt(lineInfo[1]);
//            int month = Integer.parseInt(lineInfo[2]);
//            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[4];
            String loc = lineInfo[0];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setAge(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3CenterLoc(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[4];
            String loc = lineInfo[0];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(ID);
            key.setRegion(loc);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }


    private static DataFormat loadO3Center(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[4];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(ID);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDS(String input, String group) throws IOException {
        switch (group) {
            case "pm": return loadCleanDS(input, 7);

            case "pm_filter_lower_12_zip": return loadCleanDSFilterByLowerValueZip(input, 7, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 12);
            case "pm_filter_lower_10_zip": return loadCleanDSFilterByLowerValueZip(input, 7, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 10);
            case "pm_filter_lower_8_zip": return loadCleanDSFilterByLowerValueZip(input, 7, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 8);

            case "no2_filter_lower_12_zip": return loadCleanDSFilterByLowerValueZip(input, 9, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 12);
            case "no2_filter_lower_10_zip": return loadCleanDSFilterByLowerValueZip(input, 9, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 10);
            case "no2_filter_lower_8_zip": return loadCleanDSFilterByLowerValueZip(input, 9, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 8);




            case "pm_filter_lower_12": return loadCleanDSFilterByLowerValue(input, 7, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 12);
            case "pm_filter_lower_10": return loadCleanDSFilterByLowerValue(input, 7, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 10);
            case "pm_filter_lower_8": return loadCleanDSFilterByLowerValue(input, 7, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 8);

            case "pm_ses_filter_lower_12": return loadCleanDSFilterByLowerValueSES(input, 7, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 12);
            case "pm_ses_filter_lower_10": return loadCleanDSFilterByLowerValueSES(input, 7, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 10);
            case "pm_ses_filter_lower_8": return loadCleanDSFilterByLowerValueSES(input, 7, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 8);

            case "pmc": return loadCleanDS(input, 8);
            case "pmc_filter_lower_12": return loadCleanDSFilterByLowerValue(input, 8, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 12);
            case "pmc_filter_lower_10": return loadCleanDSFilterByLowerValue(input, 8, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 10);
            case "pmc_filter_lower_8": return loadCleanDSFilterByLowerValue(input, 8, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 8);
            case "no2c_filter_lower_14": return loadCleanDSFilterByLowerValue(input, 10, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 14);
            case "no2c_filter_lower_12": return loadCleanDSFilterByLowerValue(input, 10, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 12);
            case "no2c_filter_lower_10": return loadCleanDSFilterByLowerValue(input, 10, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 10);
            case "no2c_filter_lower_8": return loadCleanDSFilterByLowerValue(input, 10, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 8);
            case "no2c_filter_lower_6": return loadCleanDSFilterByLowerValue(input, 10, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 6);
            case "no2c_filter_lower_4": return loadCleanDSFilterByLowerValue(input, 10, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 4);
            case "pm_filter": return loadCleanDSFilter(input, 9, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 8);
            case "pmc_filter": return loadCleanDSFilter(input, 10, "/scratch/wang.bin/health/DSenrollee/final/centeredPM.csv", 8);
            case "no2": return loadCleanDS(input, 9);
            case "no2c": return loadCleanDS(input, 10);
            case "no2_filter": return loadCleanDSFilter(input, 7, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 10);
            case "no2c_filter": return loadCleanDSFilter(input, 8, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 10);



            // load with regions as an extra feature
            case "pmc_area": return loadCleanDSArea(input, 8);
            case "no2c_area": return loadCleanDSArea(input, 10);

            case "pm_zip": return loadCleanDSByMonitor(input, 7, "/scratch/wang.bin/health/DSenrollee/original/no2ziplist.csv");
            case "pmc_zip": return loadCleanDSByMonitor(input, 8, "/scratch/wang.bin/health/DSenrollee/original/no2ziplist.csv");
            case "no2_zip": return loadCleanDSByMonitor(input, 9, "/scratch/wang.bin/health/DSenrollee/original/no2ziplist.csv");
            case "no2c_zip": return loadCleanDSByMonitor(input, 10, "/scratch/wang.bin/health/DSenrollee/original/no2ziplist.csv");

            case "pm_zip1": return loadCleanDSByMonitor(input, 7, "/scratch/wang.bin/health/DSenrollee/original/no2_monitor_ziopcode_bz6mile.csv");
            case "pmc_zip1": return loadCleanDSByMonitor(input, 8, "/scratch/wang.bin/health/DSenrollee/original/no2_monitor_ziopcode_bz6mile.csv");
            case "no2_zip1": return loadCleanDSByMonitor(input, 9, "/scratch/wang.bin/health/DSenrollee/original/no2_monitor_ziopcode_bz6mile.csv");
            case "no2c_zip1": return loadCleanDSByMonitor(input, 10, "/scratch/wang.bin/health/DSenrollee/original/no2_monitor_ziopcode_bz6mile.csv");

            // two features. updated
            case "pm_no2": return loadCleanDS(input, 7, 9);
            case "pm_no2_2008": return loadCleanDSByMonitor(input, 7, 9, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2008.csv");
            case "pm_no2_2012": return loadCleanDSByMonitor(input, 7, 9, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2012.csv");
            case "pm_no2_zscore": return loadCleanDSWithScaling(input, 7, 9);
            case "pm_no2_beta": return loadCleanDSWithBeta(input, 7, 9);
            case "pm_no2_residual_1_west_old": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59);
            case "pm_no2_PredBeta_1": return loadCleanDSWithPredBeta(input, 7, 9, 1.26, -1.59);
            case "pm_no2_PredBeta_2": return loadCleanDSWithPredBeta(input, 9, 7, 0.28741761, 6.78);

            // predict using the residual only.
            // residual_1: index2(no2) = bias + beta * index1(pm2.5) + residual_1
            case "pm_no2_residual_1": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59);
            case "pm_no2_residual_1_male": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 2, "1", true);
            case "pm_no2_residual_1_female": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 2, "2", true);
            case "pm_no2_residual_1_white": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 3, "1", true);
            case "pm_no2_residual_1_nonwhite": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 3, "1", false);
            case "pm_no2_residual_1_urban": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 11, "1", true);
            case "pm_no2_residual_1_nonurban": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 11, "1", false);
            case "pm_no2_residual_1_west": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 12, "3", true);
            case "pm_no2_residual_1_northeast": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 12, "2", true);
            case "pm_no2_residual_1_midwest": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 12, "1", true);
            case "pm_no2_residual_1_south": return loadCleanDSResidual(input, 7, 9, 1.26, -1.59, 12, "0", true);

            case "pm_no2_residual_1_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438);
            case "pm_no2_residual_1_male_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 2, "1", true);
            case "pm_no2_residual_1_female_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 2, "2", true);
            case "pm_no2_residual_1_white_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 3, "1", true);
            case "pm_no2_residual_1_nonwhite_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 3, "1", false);
            case "pm_no2_residual_1_urban_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 11, "1", true);
            case "pm_no2_residual_1_nonurban_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 11, "1", false);
            case "pm_no2_residual_1_west_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 12, "3", true);
            case "pm_no2_residual_1_northeast_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 12, "2", true);
            case "pm_no2_residual_1_midwest_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 12, "1", true);
            case "pm_no2_residual_1_south_2008": return loadCleanDSResidual(input, 7, 9, 1.255, -1.438, 12, "0", true);

            case "pm_no2_residual_1_urban_sep": return loadCleanDSResidual_area(input, 7, 9, 11, "1", true);
            case "pm_no2_residual_1_nonurban_sep": return loadCleanDSResidual_area(input, 7, 9,  11, "1", false);
            case "pm_no2_residual_1_west_sep": return loadCleanDSResidual_urban(input, 7, 9, 12, "3", true);
            case "pm_no2_residual_1_northeast_sep": return loadCleanDSResidual_urban(input, 7, 9, 12, "2", true);
            case "pm_no2_residual_1_midwest_sep": return loadCleanDSResidual_urban(input, 7, 9, 12, "1", true);
            case "pm_no2_residual_1_south_sep": return loadCleanDSResidual_urban(input, 7, 9, 12, "0", true);

            case "pm_no2_residual_1_WithSES": return loadCleanDSResidualWithSES(input,  9,  7, 1.255, -1.438);
            case "pm_no2_residual_1_WithSES_unnorm_zip": return loadCleanDSResidualWithSESUnNormZip(input,  9,  7, 1.255, -1.438); // with zip only

            case "pm_no2_residual_1_WithSES_unnorm": return loadCleanDSResidualWithSESUnNorm(input,  9,  7, 1.255, -1.438); // with zip + state
            case "pm_no2_residual_1_WithSES_unnorm_male": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 2, "1", true);
            case "pm_no2_residual_1_WithSES_unnorm_female": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 2, "2", true);
            case "pm_no2_residual_1_WithSES_unnorm_white": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 3, "1", true);
            case "pm_no2_residual_1_WithSES_unnorm_black": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 3, "2", true);
            case "pm_no2_residual_1_WithSES_unnorm_asian": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 3, "4", true);
            case "pm_no2_residual_1_WithSES_unnorm_hispanic": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 3, "5", true);
            case "pm_no2_residual_1_WithSES_unnorm_nonwhite": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 3, "1", false);
            case "pm_no2_residual_1_WithSES_unnorm_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true);
            case "pm_no2_residual_1_WithSES_unnorm_nonurban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", false);
            case "pm_no2_residual_1_WithSES_unnorm_west": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 12, "3", true);
            case "pm_no2_residual_1_WithSES_unnorm_northeast": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 12, "2", true);
            case "pm_no2_residual_1_WithSES_unnorm_midwest": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 12, "1", true);
            case "pm_no2_residual_1_WithSES_unnorm_south": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 12, "0", true);
            case "pm_no2_residual_1_WithSES_unnorm_more75": return loadCleanDSResidualWithSESUnNormLarger(input, 9, 7, 1.255, -1.438, 4, 75);
            case "pm_no2_residual_1_WithSES_unnorm_less": return loadCleanDSResidualWithSESUnNormEqSmaller(input, 9, 7, 1.255, -1.438, 4, 75);

            case "pm_no2_residual_2_2008": return loadCleanDSResidual(input, 9, 7,  0.2815, 7.0734);
            case "pm_no2_residual_2_male_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 2, "1", true);
            case "pm_no2_residual_2_female_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 2, "2", true);
            case "pm_no2_residual_2_white_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 3, "1", true);
            case "pm_no2_residual_2_nonwhite_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 3, "1", false);
            case "pm_no2_residual_2_urban_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 11, "1", true);
            case "pm_no2_residual_2_nonurban_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 11, "1", false);
            case "pm_no2_residual_2_west_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 12, "3", true);
            case "pm_no2_residual_2_northeast_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 12, "2", true);
            case "pm_no2_residual_2_midwest_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 12, "1", true);
            case "pm_no2_residual_2_south_2008": return loadCleanDSResidual(input, 9, 7, 0.2815,7.0734, 12, "0", true);


            case "pm_no2_residual_2_SES": return loadCleanDSResidualSES(input, 9, 7,  0.2818, -0.4968, -0.1183, 8.3650);
            case "pm_no2_residual_2_SESOnly": return loadCleanDSResidualSESOnly(input,  7,  -0.2295, -0.1170, 11.3700);
            case "pm_no2_residual_2_WithSES": return loadCleanDSResidualWithSES(input,  7,  9, 0.28741761, 6.78);
            case "pm_no2_residual_2_WithSES_unnorm_zip": return loadCleanDSResidualWithSESUnNormZip(input,  7,  9, 0.28741761, 6.78);

            case "pm_no2_residual_2_WithSES_unnorm": return loadCleanDSResidualWithSESUnNorm(input,  7,  9, 0.28741761, 6.78);
            case "pm_no2_residual_2_WithSES_unnorm_male": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 2, "1", true);
            case "pm_no2_residual_2_WithSES_unnorm_female": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 2, "2", true);
            case "pm_no2_residual_2_WithSES_unnorm_white": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 3, "1", true);
            case "pm_no2_residual_2_WithSES_unnorm_black": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 3, "2", true);
            case "pm_no2_residual_2_WithSES_unnorm_asian": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 3, "4", true);
            case "pm_no2_residual_2_WithSES_unnorm_hispanic": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 3, "5", true);
            case "pm_no2_residual_2_WithSES_unnorm_nonwhite": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 3, "1", false);
            case "pm_no2_residual_2_WithSES_unnorm_urban": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 11, "1", true);
            case "pm_no2_residual_2_WithSES_unnorm_nonurban": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 11, "1", false);
            case "pm_no2_residual_2_WithSES_unnorm_west": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 12, "3", true);
            case "pm_no2_residual_2_WithSES_unnorm_northeast": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 12, "2", true);
            case "pm_no2_residual_2_WithSES_unnorm_midwest": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 12, "1", true);
            case "pm_no2_residual_2_WithSES_unnorm_south": return loadCleanDSResidualWithSESUnNorm(input, 7, 9, 0.28741761, 6.78, 12, "0", true);
            case "pm_no2_residual_2_WithSES_unnorm_more75": return loadCleanDSResidualWithSESUnNormLarger(input, 7, 9, 0.28741761, 6.78, 4, 75);
            case "pm_no2_residual_2_WithSES_unnorm_less": return loadCleanDSResidualWithSESUnNormEqSmaller(input, 7, 9, 0.28741761, 6.78, 4, 75);



            case "pm_no2_residual_2": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.7802);
            case "pm_no2_residual_2_male": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 2, "1", true);
            case "pm_no2_residual_2_female": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 2, "2", true);
            case "pm_no2_residual_2_white": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 3, "1", true);
            case "pm_no2_residual_2_nonwhite": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 3, "1", false);
            case "pm_no2_residual_2_urban": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 11, "1", true);
            case "pm_no2_residual_2_nonurban": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 11, "1", false);
            case "pm_no2_residual_2_west": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 12, "3", true);
            case "pm_no2_residual_2_northeast": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 12, "2", true);
            case "pm_no2_residual_2_midwest": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 12, "1", true);
            case "pm_no2_residual_2_south": return loadCleanDSResidual(input, 9, 7, 0.28741761, 6.78, 12, "0", true);


            case "pm_no2_residual_2_urban_sep": return loadCleanDSResidual_2_area(input, 9, 7, 11, "1", true);
            case "pm_no2_residual_2_nonurban_sep": return loadCleanDSResidual_2_area(input, 9, 7,  11, "1", false);
            case "pm_no2_residual_2_west_sep": return loadCleanDSResidual_2_urban(input, 9, 7, 12, "3", true);
            case "pm_no2_residual_2_northeast_sep": return loadCleanDSResidual_2_urban(input, 9, 7, 12, "2", true);
            case "pm_no2_residual_2_midwest_sep": return loadCleanDSResidual_2_urban(input, 9, 7, 12, "1", true);
            case "pm_no2_residual_2_south_sep": return loadCleanDSResidual_2_urban(input, 9, 7, 12, "0", true);


            case "pm_no2_beta_1": return loadCleanDSWithBeta(input, 7, 9, 1.26, -1.59);
            case "pm_no2_beta_2": return loadCleanDSWithBeta(input, 9, 7, 0.28741761, 6.78);

            case "pm_no2_2008_no": return loadCleanDSByMonitorNoStrataOnZip(input, 7, 9, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2008.csv");
            case "pm_no2_2012_no": return loadCleanDSByMonitorNoStrataOnZip(input, 7, 9, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2012.csv");

            case "pm_2008": return loadCleanDSByMonitorByFatemeh(input, 7, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2008.csv");
            case "no2_2008": return loadCleanDSByMonitorByFatemeh(input, 9, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2008.csv");
            case "pm_2012": return loadCleanDSByMonitorByFatemeh(input, 7, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2012.csv");
            case "no2_2012": return loadCleanDSByMonitorByFatemeh(input, 9, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2012.csv");

            case "pm_no2_west_2008": return loadCleanDS(input, 7, 9,12, "3", true, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2008.csv");
            case "pm_no2_northeast_2008": return loadCleanDS(input, 7, 9, 12, "2", true, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2008.csv");
            case "pm_no2_midwest_2008": return loadCleanDS(input, 7, 9,12, "1", true, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2008.csv");
            case "pm_no2_south_2008": return loadCleanDS(input, 7, 9,12, "0", true, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2008.csv");
            case "pm_no2_west_2012": return loadCleanDS(input, 7, 9,12, "3", true, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2012.csv");
            case "pm_no2_northeast_2012": return loadCleanDS(input, 7, 9, 12, "2", true, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2012.csv");
            case "pm_no2_midwest_2012": return loadCleanDS(input, 7, 9,12, "1", true, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2012.csv");
            case "pm_no2_south_2012": return loadCleanDS(input, 7, 9,12, "0", true, "/gss_gpfs_scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2012.csv");

            case "pm_no2_male": return loadCleanDS(input, 7, 9, 2, "1", true);
            case "pm_no2_female": return loadCleanDS(input, 7, 9, 2, "2", true);
            case "pm_no2_white": return loadCleanDS(input, 7, 9,3, "1", true);
            case "pm_no2_nonwhite": return loadCleanDS(input, 7, 9,3, "1", false);
            case "pm_no2_urban": return loadCleanDS(input, 7, 9,11, "1", true);
            case "pm_no2_nonurban": return loadCleanDS(input, 7, 9,11, "1", false);
            case "pm_no2_west": return loadCleanDS(input, 7, 9,12, "3", true);
            case "pm_no2_northeast": return loadCleanDS(input, 7, 9, 12, "2", true);
            case "pm_no2_midwest": return loadCleanDS(input, 7, 9,12, "1", true);
            case "pm_no2_south": return loadCleanDS(input, 7, 9,12, "0", true);
            case "pm_no2_less75": return loadCleanDSLEQAge(input, 7, 9,75);
            case "pm_no2_more75": return loadCleanDSLargerAge(input, 7, 9,75);

            case "pmc_no2c_kido": return loadKiDoTwoFeatures(input, 8, 9);
            case "no2c_pmc": return loadCleanDS(input, 10, 8);
            case "pmc_no2c": return loadCleanDS(input, 8, 10);

            case "pmc_no2c_male": return loadCleanDS(input, 8, 10, 2, "1", true);
            case "pmc_no2c_female": return loadCleanDS(input, 8, 10, 2, "2", true);
            case "pmc_no2c_white": return loadCleanDS(input, 8, 10,3, "1", true);
            case "pmc_no2c_nonwhite": return loadCleanDS(input, 8, 10,3, "1", false);
            case "pmc_no2c_urban": return loadCleanDS(input, 8, 10,11, "1", true);
            case "pmc_no2c_nonurban": return loadCleanDS(input, 8, 10,11, "1", false);
            case "pmc_no2c_west": return loadCleanDS(input, 8, 10,12, "3", true);
            case "pmc_no2c_northeast": return loadCleanDS(input, 8, 10, 12, "2", true);
            case "pmc_no2c_midwest": return loadCleanDS(input, 8, 10,12, "1", true);
            case "pmc_no2c_south": return loadCleanDS(input, 8, 10,12, "0", true);
            case "pmc_no2c_less75": return loadCleanDSLEQAge(input, 8, 10,75);
            case "pmc_no2c_more75": return loadCleanDSLargerAge(input, 8, 10,75);

            // ************ tow features with area as extra feature **************
            case "pmc_no2c_area": return loadCleanDSArea(input, 8, 10);
            case "pmc_no2c_area_male": return loadCleanDSArea(input, 8, 10, 2, "1", true);
            case "pmc_no2c_area_female": return loadCleanDSArea(input, 8, 10, 2, "2", true);
            case "pmc_no2c_area_white": return loadCleanDSArea(input, 8, 10,3, "1", true);
            case "pmc_no2c_area_nonwhite": return loadCleanDSArea(input, 8, 10,3, "1", false);
            case "pmc_no2c_area_urban": return loadCleanDSArea(input, 8, 10,11, "1", true);
            case "pmc_no2c_area_nonurban": return loadCleanDSArea(input, 8, 10,11, "1", false);
            case "pmc_no2c_area_west": return loadCleanDSArea(input, 8, 10,12, "3", true);
            case "pmc_no2c_area_northeast": return loadCleanDSArea(input, 8, 10, 12, "2", true);
            case "pmc_no2c_area_midwest": return loadCleanDSArea(input, 8, 10,12, "1", true);
            case "pmc_no2c_area_south": return loadCleanDSArea(input, 8, 10,12, "0", true);
            case "pmc_no2c_area_less75": return loadCleanDSLEQAgeArea(input, 8, 10,75);
            case "pmc_no2c_area_more75": return loadCleanDSLargerAgeArea(input, 8, 10,75);

            case "pm_no2c": return loadCleanDS(input, 7, 10);
            case "pmc_no2": return loadCleanDS(input, 8, 9);

            /////////////  end of two features. updated

            // three features. updated
            case "pmNno2": return loadCleanDSN(input, 7, 9);
            case "pmNno2_male": return loadCleanDSN(input, 7, 9, 2, "1", true);
            case "pmNno2_female": return loadCleanDSN(input, 7, 9, 2, "2", true);
            case "pmNno2_white": return loadCleanDSN(input, 7, 9,3, "1", true);
            case "pmNno2_nonwhite": return loadCleanDSN(input, 7, 9,3, "1", false);
            case "pmNno2_urban": return loadCleanDSN(input, 7, 9,11, "1", true);
            case "pmNno2_nonurban": return loadCleanDSN(input, 7, 9,11, "1", false);
            case "pmNno2_west": return loadCleanDSN(input, 7, 9,12, "3", true);
            case "pmNno2_northeast": return loadCleanDSN(input, 7, 9, 12, "2", true);
            case "pmNno2_midwest": return loadCleanDSN(input, 7, 9,12, "1", true);
            case "pmNno2_south": return loadCleanDSN(input, 7, 9,12, "0", true);
            case "pmNno2_less75": return loadCleanDSNLEQAge(input, 7, 9,75);
            case "pmNno2_more75": return loadCleanDSNLargerAge(input, 7, 9,75);
            ///////////// end of // three features. updated


            case "pm_strataAge": return loadCleanDSStrataAge(input, 7);
            case "pmc_strataAge": return loadCleanDSStrataAge(input, 8);
            case "no2_strataAge": return loadCleanDSStrataAge(input, 9);
            case "no2c_strataAge": return loadCleanDSStrataAge(input, 10);
            case "pm_noStrata": return loadCleanDSNoStrata(input, 7);
            case "pmc_noStrata": return loadCleanDSNoStrata(input, 8);
            case "no2_noStrata": return loadCleanDSNoStrata(input, 9);
            case "no2c_noStrata": return loadCleanDSNoStrata(input, 10);

            case "pm_noStrataNoDate": return loadCleanDSNoStrataNoDate(input, 7);

            case "pm_regions_strataAge": return loadCleanDSRegionsStrataAge(input, 7);
            case "pmc_regions_strataAge": return loadCleanDSRegionsStrataAge(input, 8);
            case "no2_regions_strataAge": return loadCleanDSRegionsStrataAge(input, 9);
            case "no2c_regions_strataAge": return loadCleanDSRegionsStrataAge(input, 10);
            case "pm_regions_noStrata": return loadCleanDSRegionsNoStrata(input, 7);
            case "pmc_regions_noStrata": return loadCleanDSRegionsNoStrata(input, 8);
            case "no2_regions_noStrata": return loadCleanDSRegionsNoStrata(input, 9);
            case "no2c_regions_noStrata": return loadCleanDSRegionsNoStrata(input, 10);
            case "no2c_region_noStrata": return loadCleanDSRegionNoStrata(input, 10);

            case "no2c_area_noStrata": return loadCleanDSDumpNoStrata(input, 10, 12);
            case "no2_area_noStrata": return loadCleanDSDumpNoStrata(input, 9, 12);
            case "no2c_state_noStrata": return loadCleanDSDumpNoStrata(input, 10, 13);
            case "no2_state_noStrata": return loadCleanDSDumpNoStrata(input, 9, 13);

            // ************* PM *********************
            case "pm_male": return loadCleanDS(input, 7, 2, "1", true);
            case "pm_female": return loadCleanDS(input, 7, 2, "2", true);
            case "pm_white": return loadCleanDS(input, 7, 3, "1", true);
            case "pm_nonwhite": return loadCleanDS(input, 7, 3, "1", false);
            case "pm_urban": return loadCleanDS(input, 7, 11, "1", true);
            case "pm_nonurban": return loadCleanDS(input, 7, 11, "1", false);
            case "pm_west": return loadCleanDS(input, 7, 12, "3", true);
            case "pm_northeast": return loadCleanDS(input, 7, 12, "2", true);
            case "pm_midwest": return loadCleanDS(input, 7, 12, "1", true);
            case "pm_south": return loadCleanDS(input, 7, 12, "0", true);
            case "pm_less75": return loadCleanDSLEQAge(input, 7, 75);
            case "pm_more75": return loadCleanDSLargerAge(input, 7, 75);



            // -------strata on age only-----------
            case "pm_northeast_strataAge": return loadCleanDSStrataAge(input, 7, 12, "NORTHEAST", true);
            case "pm_midwest_strataAge": return loadCleanDSStrataAge(input, 7, 12, "MIDWEST", true);
            case "pm_south_strataAge": return loadCleanDSStrataAge(input, 7, 12, "SOUTH", true);
            case "pm_west_strataAge": return loadCleanDSStrataAge(input, 7, 12, "WEST", true);
            case "pm_northeast_noStrata": return loadCleanDSNoStrata(input, 7, 12, "NORTHEAST", true);
            case "pm_midwest_noStrata": return loadCleanDSNoStrata(input, 7, 12, "MIDWEST", true);
            case "pm_south_noStrata": return loadCleanDSNoStrata(input, 7, 12, "SOUTH", true);
            case "pm_west_noStrata": return loadCleanDSNoStrata(input, 7, 12, "WEST", true);


            // ************* PMC *********************
            case "pmc_male": return loadCleanDS(input, 8, 2, "1", true, 13);
            case "pmc_female": return loadCleanDS(input, 8, 2, "2", true, 13);
            case "pmc_white": return loadCleanDS(input, 8, 3, "1", true, 13);
            case "pmc_nonwhite": return loadCleanDS(input, 8, 3, "1", false, 13);
            case "pmc_urban": return loadCleanDS(input, 8, 11, "1", true, 13);
            case "pmc_suburban": return loadCleanDS(input, 8, 11, "2", true, 13);
            case "pmc_rural": return loadCleanDS(input, 8, 11, "3", true, 13);
            case "pmc_nonurban": return loadCleanDS(input, 8, 11, "1", false,13);
            case "pmc_west": return loadCleanDS(input, 8, 12, "3", true, 13);
            case "pmc_northeast": return loadCleanDS(input, 8, 12, "2", true, 13);
            case "pmc_midwest": return loadCleanDS(input, 8, 12, "1", true, 13);
            case "pmc_south": return loadCleanDS(input, 8, 12, "0", true, 13);
            case "pmc_less75": return loadCleanDSLEQAge(input, 8, 75);
            case "pmc_more75": return loadCleanDSLargerAge(input, 8, 75);

            // ************** PMC + Area *******************
            case "pmc_area_male": return loadCleanDSArea(input, 8, 2, "1", true);
            case "pmc_area_female": return loadCleanDSArea(input, 8, 2, "2", true);
            case "pmc_area_white": return loadCleanDSArea(input, 8, 3, "1", true);
            case "pmc_area_nonwhite": return loadCleanDSArea(input, 8, 3, "1", false);
            case "pmc_area_urban": return loadCleanDSArea(input, 8, 11, "1", true);
            case "pmc_area_nonurban": return loadCleanDSArea(input, 8, 11, "1", false);
            case "pmc_area_west": return loadCleanDSArea(input, 8, 12, "3", true);
            case "pmc_area_northeast": return loadCleanDSArea(input, 8, 12, "2", true);
            case "pmc_area_midwest": return loadCleanDSArea(input, 8, 12, "1", true);
            case "pmc_area_south": return loadCleanDSArea(input, 8, 12, "0", true);
            case "pmc_area_less75": return loadCleanDSLEQAgeStrata(input, 8, 75, 13);
            case "pmc_area_more75": return loadCleanDSLEQAgeStrata(input, 8, 75, 13);

            // -------strata on state filter by lower PM value
            // -------strata on state-------------
            // 01/02/2019: update add the filter for year before 2008.
            case "pm_strata_state": return loadCleanDSStrata(input, 7, 13);
            case "pm_strata_state_male": return loadCleanDS(input, 7, 2, "1", true, 13);
            case "pm_strata_state_female": return loadCleanDS(input, 7, 2, "2", true, 13);
            case "pm_strata_state_white": return loadCleanDS(input, 7, 3, "1", true, 13);
            case "pm_strata_state_nonwhite": return loadCleanDS(input, 7, 3, "1", false, 13);
            case "pm_strata_state_black": return loadCleanDS(input, 7, 3, "2", true, 13);
            case "pm_strata_state_asian": return loadCleanDS(input, 7, 3, "4", true, 13);
            case "pm_strata_state_hispanic": return loadCleanDS(input, 7, 3, "5", true, 13);
            case "pm_strata_state_urban": return loadCleanDS(input, 7, 11, "1", true, 13);
            case "pm_strata_state_suburban": return loadCleanDS(input, 7, 11, "2", true, 13);
            case "pm_strata_state_rural": return loadCleanDS(input, 7, 11, "3", true, 13);
            case "pm_strata_state_nonurban": return loadCleanDS(input, 7, 11, "1", false, 13);
            case "pm_strata_state_west": return loadCleanDS(input, 7, 12, "3", true, 13);
            case "pm_strata_state_northeast": return loadCleanDS(input, 7, 12, "2", true, 13);
            case "pm_strata_state_midwest": return loadCleanDS(input, 7, 12, "1", true, 13);
            case "pm_strata_state_south": return loadCleanDS(input, 7, 12, "0", true, 13);
            case "pm_strata_state_less75": return loadCleanDSLEQAgeStrata(input, 7, 75, 13);
            case "pm_strata_state_more75": return loadCleanDSLargerAgeStrata(input, 7, 75, 13);
            // ----
            case "pm_strata_state_threshold_10": return loadCleanDSStrata(input, 7, 13, 7, 10.0);
            case "pm_strata_state_threshold_12": return loadCleanDSStrata(input, 7, 13, 7, 12.0);

            // --------Updated on 09/08/2018-----------
            /**
             * updates: model for PM2.5 components.
             *  7. V
             *  8. Si
             *  9. Zn
             * 10. NO3
             * 11. EC_tor
             * 12. EC_tot
             * 13. SO4
             */
            case "V_strata_zip": return loadCleanDS_PM_Components(input, 7, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "Si_strata_zip": return loadCleanDS_PM_Components(input, 8, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "Zn_strata_zip": return loadCleanDS_PM_Components(input, 9, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "NO3_strata_zip": return loadCleanDS_PM_Components(input, 10, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "EC_tor_strata_zip": return loadCleanDS_PM_Components(input, 11, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "EC_tot_strata_zip": return loadCleanDS_PM_Components(input, 12, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "SO4_strata_zip": return loadCleanDS_PM_Components(input, 13, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "Ni_strata_zip": return loadCleanDS_PM_Components(input, 14, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "dump_V_strata_zip": return dumpCleanDS_PM_Components(input, 7, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv",
                    "/scratch/wang.bin/health/DSenrollee/PMComponents/V");
            case "dump_Si_strata_zip": return dumpCleanDS_PM_Components(input, 8, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv",
                    "/scratch/wang.bin/health/DSenrollee/PMComponents/Si");
            case "dump_Zn_strata_zip": return dumpCleanDS_PM_Components(input, 9, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv",
                    "/scratch/wang.bin/health/DSenrollee/PMComponents/Zn");
            case "dump_NO3_strata_zip": return dumpCleanDS_PM_Components(input, 10, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv",
                    "/scratch/wang.bin/health/DSenrollee/PMComponents/NO3");
            case "dump_EC_tor_strata_zip": return dumpCleanDS_PM_Components(input, 11, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv",
                    "/scratch/wang.bin/health/DSenrollee/PMComponents/EC_tor");
            case "dump_EC_tot_strata_zip": return dumpCleanDS_PM_Components(input, 12, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv",
                    "/scratch/wang.bin/health/DSenrollee/PMComponents/EC_tot");
            case "dump_SO4_strata_zip": return dumpCleanDS_PM_Components(input, 13, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv",
                    "/scratch/wang.bin/health/DSenrollee/PMComponents/SO4");

            case "pm_V_strata_zip": return loadCleanDS_PM_Components(input, 7,7, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_Si_strata_zip": return loadCleanDS_PM_Components(input, 7,8, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_Zn_strata_zip": return loadCleanDS_PM_Components(input, 7,9, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_NO3_strata_zip": return loadCleanDS_PM_Components(input, 7,10, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_EC_tor_strata_zip": return loadCleanDS_PM_Components(input, 7,11, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_EC_tot_strata_zip": return loadCleanDS_PM_Components(input, 7,12, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_SO4_strata_zip": return loadCleanDS_PM_Components(input, 7,13, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");

            case "pm_alone_V_strata_zip": return loadCleanDS_PM_Components_alone(input, 7,7, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_alone_Si_strata_zip": return loadCleanDS_PM_Components_alone(input, 7,8, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_alone_Zn_strata_zip": return loadCleanDS_PM_Components_alone(input, 7,9, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_alone_NO3_strata_zip": return loadCleanDS_PM_Components_alone(input, 7,10, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_alone_EC_tor_strata_zip": return loadCleanDS_PM_Components_alone(input, 7,11, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_alone_EC_tot_strata_zip": return loadCleanDS_PM_Components_alone(input, 7,12, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "pm_alone_SO4_strata_zip": return loadCleanDS_PM_Components_alone(input, 7,13, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");


            case "SO4_EC_tor_strata_zip": return loadCleanDS_PM_Components_1(input, 13,11, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "SO4_EC_tot_strata_zip": return loadCleanDS_PM_Components_1(input, 13,12, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "SO4_NO3_strata_zip": return loadCleanDS_PM_Components_1(input, 13,10, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "NO3_EC_tor_strata_zip": return loadCleanDS_PM_Components_1(input, 10,11, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "NO3_EC_tot_strata_zip": return loadCleanDS_PM_Components_1(input, 10,12, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "SO4_Si_strata_zip": return loadCleanDS_PM_Components_1(input, 13,8, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "Si_EC_tor_strata_zip": return loadCleanDS_PM_Components_1(input, 8,11, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");
            case "Si_EC_tot_strata_zip": return loadCleanDS_PM_Components_1(input, 8,12, "/scratch/wang.bin/health/DSenrollee/original/speciation_zipcodecsv.csv");


            // --------strata on zipcode-----------
            case "pm_strata_zip": return loadCleanDSStrata(input, 7, 0);
            case "pm_strata_zip_male": return loadCleanDS(input, 7, 2, "1", true, 0);
            case "pm_strata_zip_female": return loadCleanDS(input, 7, 2, "2", true, 0);
            case "pm_strata_zip_white": return loadCleanDS(input, 7, 3, "1", true, 0);
            case "pm_strata_zip_nonwhite": return loadCleanDS(input, 7, 3, "1", false, 0);
            case "pm_strata_zip_black": return loadCleanDS(input, 7, 3, "2", true, 0);
            case "pm_strata_zip_asian": return loadCleanDS(input, 7, 3, "4", true, 0);
            case "pm_strata_zip_hispanic": return loadCleanDS(input, 7, 3, "5", true, 0);
            case "pm_strata_zip_native": return loadCleanDS(input, 7, 3, "6", true, 0);

            case "pm_strata_zip_races_as_feature": return loadCleanDSStrataRaceFeature(input, 7, 0);
            case "pm_strata_zip_races_as_feature_gross_feature_unnorm": return loadCleanDSStrataRaceFeatureGrossFeature(input, 7, 0);
            case "pm_strata_zip_black_as_feature_gross_feature_unnorm": return loadCleanDSStrataBlackFeatureGrossFeature(input, 7, 0);
//            case "pm_strata_zip_SES_as_feature": return loadCleanDSStrataSESFeature(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 0);

            case "pm_strata_zip_urban": return loadCleanDS(input, 7, 11, "1", true, 0);
            case "pm_strata_zip_suburban": return loadCleanDS(input, 7, 11, "2", true, 0);
            case "pm_strata_zip_rural": return loadCleanDS(input, 7, 11, "3", true, 0);

            case "pm_strata_zip_urban_usda": return loadCleanDSUSDA(input, 7, 0, "/scratch/wang.bin/health/DSenrollee/final_1/urbanCode.csv", 3, true);
            case "pm_strata_zip_nonurban_usda": return loadCleanDSUSDA(input, 7, 0, "/scratch/wang.bin/health/DSenrollee/final_1/urbanCode.csv", 3, false);

            case "pm_strata_zip_nonurban": return loadCleanDS(input, 7, 11, "1", false, 0);
            case "pm_strata_zip_west": return loadCleanDS(input, 7, 12, "3", true, 0);
            case "pm_strata_zip_northeast": return loadCleanDS(input, 7, 12, "2", true, 0);
            case "pm_strata_zip_midwest": return loadCleanDS(input, 7, 12, "1", true, 0);
            case "pm_strata_zip_south": return loadCleanDS(input, 7, 12, "0", true, 0);
            case "pm_strata_zip_less75": return loadCleanDSLEQAgeStrata(input, 7, 75, 0);
            case "pm_strata_zip_more75": return loadCleanDSLargerAgeStrata(input, 7, 75, 0);

//            case "pm_strata_zip_gross": return loadCleanDSStrataGross(input, 7, 0);
//            case "pm_strata_zip_gross_low": return loadCleanDSStrataGrossFiliter(input, 7, 0, 0.778, true);
//            case "pm_strata_zip_gross_high": return loadCleanDSStrataGrossFiliter(input, 7, 0, 0.778, false);


            case "pm_strata_zip_not_white": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,4}, 3, "1");
            case "pm_strata_zip_not_black": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,4}, 3, "2");
            case "pm_strata_zip_not_asian": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,4}, 3, "4");
            case "pm_strata_zip_not_hispanic": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,4}, 3, "5");
            case "pm_strata_zip_not_nonwhite": return loadCleanDSStrataEqualFeatureGrossFeature(input, 7, new int[]{0,2,4}, 3, "1");

            case "pm_strata_zip_not_urban": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,3,4}, 11, "1");
            case "pm_strata_zip_not_suburban": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,3,4}, 11, "2");
            case "pm_strata_zip_not_rural": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,3,4}, 11, "3");
            case "pm_strata_zip_not_nonurban": return loadCleanDSStrataEqualFeatureGrossFeature(input, 7, new int[]{0,2,3,4}, 11, "1");

            // {'WEST': 3, 'NORTHEAST': 2, 'MIDWEST': 1, 'SOUTH': 0}
            case "pm_strata_zip_not_south": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,3,4}, 12, "0");
            case "pm_strata_zip_not_midwest": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,3,4}, 12, "1");
            case "pm_strata_zip_not_northeast": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,3,4}, 12, "2");
            case "pm_strata_zip_not_west": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,2,3,4}, 12, "3");

            case "pm_strata_zip_not_male": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,3,4}, 2, "1");
            case "pm_strata_zip_not_female": return loadCleanDSStrataNotFeatureGrossFeature(input, 7, new int[]{0,3,4}, 2, "2");

            case "pm_strata_zip_not_more75": return loadCleanDSStrataNotMoreFeatureGrossFeature(input, 7, new int[]{0,2,3}, 4, 75);
            case "pm_strata_zip_not_less75": return loadCleanDSStrataNotLessFeatureGrossFeature(input, 7, new int[]{0,2,3}, 4, 75);


            case "pm_strata_zip_not_poor": return loadCleanDSStrataSESFeature(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1);
            case "pm_strata_zip_not_middle": return loadCleanDSStrataSESFeature(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2);
            case "pm_strata_zip_not_rich": return loadCleanDSStrataSESFeature(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3);

            // Mortality ~ PM2.5 + (Not-low-income_ZIP * PM2.5)
            case "pm_strata_zip_not_poor_no_indicator": return loadCleanDSStrataSESFeature_no_indicator(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1);
            case "pm_strata_zip_not_middle_no_indicator": return loadCleanDSStrataSESFeature_no_indicator(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2);
            case "pm_strata_zip_not_rich_no_indicator": return loadCleanDSStrataSESFeature_no_indicator(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3);

            //  Mortality ~ PM2.5 + (Not-low-income_ZIP * PM2.5) + SES_state.
            case "pm_strata_zip_not_poor_state_no_indicator": return loadCleanDSStrataSESFeatureState_no_indicator(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1);
            case "pm_strata_zip_not_middle_state_no_indicator": return loadCleanDSStrataSESFeatureState_no_indicator(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2);
            case "pm_strata_zip_not_rich_state_no_indicator": return loadCleanDSStrataSESFeatureState_no_indicator(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3);


            case "pm_strata_zip_not_poor_urban": return loadCleanDSStrataSESFeature(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1, 11, "1");
            case "pm_strata_zip_not_middle_urban": return loadCleanDSStrataSESFeature(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2, 11, "1");
            case "pm_strata_zip_not_rich_urban": return loadCleanDSStrataSESFeature(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3, 11, "1");

            case "pm_strata_zip_not_poor_state": return loadCleanDSStrataSESFeatureState(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1);
            case "pm_strata_zip_not_middle_state": return loadCleanDSStrataSESFeatureState(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2);
            case "pm_strata_zip_not_rich_state": return loadCleanDSStrataSESFeatureState(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3);

            case "pm_strata_zip_not_poor_urban_state": return loadCleanDSStrataSESFeatureState(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1, 11, "1");
            case "pm_strata_zip_not_middle_urban_state": return loadCleanDSStrataSESFeatureState(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2, 11, "1");
            case "pm_strata_zip_not_rich_urban_state": return loadCleanDSStrataSESFeatureState(input, 7, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3, 11, "1");


            case "no2_strata_zip_not_white": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,4}, 3, "1");
            case "no2_strata_zip_not_black": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,4}, 3, "2");
            case "no2_strata_zip_not_asian": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,4}, 3, "4");
            case "no2_strata_zip_not_hispanic": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,4}, 3, "5");
            case "no2_strata_zip_not_nonwhite": return loadCleanDSStrataEqualFeatureGrossFeature(input, 9, new int[]{0,2,4}, 3, "1");

            case "no2_strata_zip_not_urban": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,3,4}, 11, "1");
            case "no2_strata_zip_not_suburban": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,3,4}, 11, "2");
            case "no2_strata_zip_not_rural": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,3,4}, 11, "3");
            case "no2_strata_zip_not_nonurban": return loadCleanDSStrataEqualFeatureGrossFeature(input, 9, new int[]{0,2,3,4}, 11, "1");

            case "no2_strata_zip_not_male": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,3,4}, 2, "1");
            case "no2_strata_zip_not_female": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,3,4}, 2, "2");

            case "no2_strata_zip_not_more75": return loadCleanDSStrataNotMoreFeatureGrossFeature(input, 9, new int[]{0,2,3}, 4, 75);
            case "no2_strata_zip_not_less75": return loadCleanDSStrataNotLessFeatureGrossFeature(input, 9, new int[]{0,2,3}, 4, 75);


            case "no2_strata_zip_not_poor": return loadCleanDSStrataSESFeature(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1);
            case "no2_strata_zip_not_middle": return loadCleanDSStrataSESFeature(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2);
            case "no2_strata_zip_not_rich": return loadCleanDSStrataSESFeature(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3);
            // {'WEST': 3, 'NORTHEAST': 2, 'MIDWEST': 1, 'SOUTH': 0}
            case "no2_strata_zip_not_south": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,3,4}, 12, "0");
            case "no2_strata_zip_not_midwest": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,3,4}, 12, "1");
            case "no2_strata_zip_not_northeast": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,3,4}, 12, "2");
            case "no2_strata_zip_not_west": return loadCleanDSStrataNotFeatureGrossFeature(input, 9, new int[]{0,2,3,4}, 12, "3");


            case "pm_no2_residual_1_WithSES_unnorm_not_male": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "1", true,
                    new int[]{0,3,4}, 2, "1");
            case "pm_no2_residual_1_WithSES_unnorm_not_female": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "2", true,
                    new int[]{0,3,4}, 2, "2");
            case "pm_no2_residual_1_WithSES_unnorm_not_white": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "1", true,
                    new int[]{0,2,4}, 3, "1");
            case "pm_no2_residual_1_WithSES_unnorm__not_black": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "2", true,
                    new int[]{0,2,4}, 3, "2");
            case "pm_no2_residual_1_WithSES_unnorm_not_asian": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "4", true,
                    new int[]{0,2,4}, 3, "4");
            case "pm_no2_residual_1_WithSES_unnorm_not_hispanic": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "5", true,
                    new int[]{0,2,4}, 3, "5");
            case "pm_no2_residual_1_WithSES_unnorm_not_nonwhite": return loadCleanDSResidualWithSESUnNormEqual(input, 9, 7, 1.255, -1.438, -1, "1", false,
                    new int[]{0,2,4}, 3, "1");

            case "pm_no2_residual_1_WithSES_unnorm_not_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "1", true,
                    new int[]{0,2,3,4}, 11, "1");
            case "pm_no2_residual_1_WithSES_unnorm_not_suburban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "1", true,
                    new int[]{0,2,3,4}, 11, "2");
            case "pm_no2_residual_1_WithSES_unnorm_not_rural": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "1", true,
                    new int[]{0,2,3,4}, 11, "3");
            case "pm_no2_residual_1_WithSES_unnorm_not_nonurban": return loadCleanDSResidualWithSESUnNormEqual(input, 9, 7, 1.255, -1.438, -1, "1", true,
                    new int[]{0,2,3,4}, 11, "1");


            case "pm_no2_residual_1_WithSES_unnorm_not_west": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "3", true,
                    new int[]{0,2,3,4}, 12, "3");
            case "pm_no2_residual_1_WithSES_unnorm_not_northeast": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "2", true,
                    new int[]{0,2,3,4}, 12, "2");
            case "pm_no2_residual_1_WithSES_unnorm_not_midwest": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "1", true,
                    new int[]{0,2,3,4}, 12, "1");
            case "pm_no2_residual_1_WithSES_unnorm_not_south": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, -1, "0", true,
                    new int[]{0,2,3,4}, 12, "0");


            case "pm_no2_residual_1_WithSES_unnorm_not_more75": return loadCleanDSResidualWithSESUnNormNotMore(input, 9, 7, 1.255, -1.438, -1, "75", true,
                    new int[]{0,2,3}, 4, 75);
            case "pm_no2_residual_1_WithSES_unnorm_not_less": return loadCleanDSResidualWithSESUnNormNotLess(input, 9, 7, 1.255, -1.438, -1, "75", true,
                    new int[]{0,2,3}, 4, 75);


            case "pm_no2_residual_1_not_low_tertiles_SES": return loadCleanDSStrataSESFeatureLowerTertiles(input, 9,7, 1.255, -1.438, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv",  new double[]{7.02275, 12.046972}, 1);
            case "pm_no2_residual_1_not_middle_tertiles_SES": return loadCleanDSStrataSESFeatureLowerTertiles(input, 9,7, 1.255, -1.438, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", new double[]{7.02275, 12.046972}, 2);
            case "pm_no2_residual_1_not_high_tertiles_SES": return loadCleanDSStrataSESFeatureLowerTertiles(input, 9,7, 1.255, -1.438, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", new double[]{7.02275, 12.046972}, 3);

            case "pm_no2_residual_1_not_poor": return loadCleanDSStrataSESFeature(input, 9, 7, 1.255, -1.438, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1 );
            case "pm_no2_residual_1_not_middle": return loadCleanDSStrataSESFeature(input, 9, 7, 1.255, -1.438, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2);
            case "pm_no2_residual_1_not_rich": return loadCleanDSStrataSESFeature(input, 9, 7, 1.255, -1.438, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3);



            case "pm_no2_residual_1_WithSES_unnorm_not_male_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,3,4}, 2, "1");
            case "pm_no2_residual_1_WithSES_unnorm_not_female_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,3,4}, 2, "2");
            case "pm_no2_residual_1_WithSES_unnorm_not_white_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,4}, 3, "1");
            case "pm_no2_residual_1_WithSES_unnorm_not_black_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,4}, 3, "2");
            case "pm_no2_residual_1_WithSES_unnorm_not_asian_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,4}, 3, "4");
            case "pm_no2_residual_1_WithSES_unnorm_not_hispanic_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,4}, 3, "5");
            case "pm_no2_residual_1_WithSES_unnorm_not_nonwhite_urban": return loadCleanDSResidualWithSESUnNormEqual(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,4}, 3, "1");


            case "pm_no2_residual_1_WithSES_unnorm_not_west_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,3,4}, 12, "3");
            case "pm_no2_residual_1_WithSES_unnorm_not_northeast_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,3,4}, 12, "2");
            case "pm_no2_residual_1_WithSES_unnorm_not_midwest_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,3,4}, 12, "1");
            case "pm_no2_residual_1_WithSES_unnorm_not_south_urban": return loadCleanDSResidualWithSESUnNorm(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,3,4}, 12, "0");


            case "pm_no2_residual_1_WithSES_unnorm_not_more75_urban": return loadCleanDSResidualWithSESUnNormNotMore(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,3}, 4, 75);
            case "pm_no2_residual_1_WithSES_unnorm_not_less75_urban": return loadCleanDSResidualWithSESUnNormNotLess(input, 9, 7, 1.255, -1.438, 11, "1", true,
                    new int[]{0,2,3}, 4, 75);

            case "pm_no2_residual_1_not_low_tertiles_SES_urban": return loadCleanDSStrataSESFeatureLowerTertiles(input, 9,7, 1.255, -1.438, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv",  new double[]{7.02275, 12.046972}, 1, 11, "1");
            case "pm_no2_residual_1_not_middle_tertiles_SES_urban": return loadCleanDSStrataSESFeatureLowerTertiles(input, 9, 7, 1.255, -1.438, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", new double[]{7.02275, 12.046972}, 2, 11, "1");
            case "pm_no2_residual_1_not_high_tertiles_SES_urban": return loadCleanDSStrataSESFeatureLowerTertiles(input, 9, 7, 1.255, -1.438, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", new double[]{7.02275, 12.046972}, 3, 11, "1");

            case "pm_no2_residual_1_not_poor_urban": return loadCleanDSStrataSESFeature(input, 9, 7, 1.255, -1.438, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1, 11, "1");
            case "pm_no2_residual_1_not_middle_urban": return loadCleanDSStrataSESFeature(input, 9, 7, 1.255, -1.438, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2, 11, "1");
            case "pm_no2_residual_1_not_rich_urban": return loadCleanDSStrataSESFeature(input, 9, 7, 1.255, -1.438, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3, 11, "1");



            case "no2_strata_zip_not_low_tertiles_SES": return loadCleanDSStrataSESFeatureLowerTertiles(input, 9, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv",  new double[]{7.02275, 12.046972}, 1);
            case "no2_strata_zip_not_middle_tertiles_SES": return loadCleanDSStrataSESFeatureLowerTertiles(input, 9, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", new double[]{7.02275, 12.046972}, 2);
            case "no2_strata_zip_not_high_tertiles_SES": return loadCleanDSStrataSESFeatureLowerTertiles(input, 9, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", new double[]{7.02275, 12.046972}, 3);


            case "no2_strata_zip_not_low_tertiles": return loadCleanDSStrataLowerTertiles(input, 9, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv",new double[]{7.02275, 12.046972}, 1);
            case "no2_strata_zip_not_middle_tertiles": return loadCleanDSStrataLowerTertiles(input, 9, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv",new double[]{7.02275, 12.046972}, 2);
            case "no2_strata_zip_not_high_tertiles": return loadCleanDSStrataLowerTertiles(input, 9, new int[]{0,2,3,4}, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv",new double[]{7.02275, 12.046972}, 3);


            case "no2_strata_zip_not_poor_urban": return loadCleanDSStrataSESFeature(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1, 11, "1");
            case "no2_strata_zip_not_middle_urban": return loadCleanDSStrataSESFeature(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2, 11, "1");
            case "no2_strata_zip_not_rich_urban": return loadCleanDSStrataSESFeature(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3, 11, "1");


            case "no2_strata_zip_not_poor_state": return loadCleanDSStrataSESFeatureState(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1);
            case "no2_strata_zip_not_middle_state": return loadCleanDSStrataSESFeatureState(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2);
            case "no2_strata_zip_not_rich_state": return loadCleanDSStrataSESFeatureState(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3);

            case "no2_strata_zip_not_poor_urban_state": return loadCleanDSStrataSESFeatureState(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1,11, "1");
            case "no2_strata_zip_not_middle_urban_state": return loadCleanDSStrataSESFeatureState(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2,11, "1");
            case "no2_strata_zip_not_rich_urban_state": return loadCleanDSStrataSESFeatureState(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3,11, "1");


            // Mortality ~ No2 + (Not-low-income_ZIP * No2).
            case "no2_strata_zip_not_poor_no_indicator": return loadCleanDSStrataSESFeature_no_indicator(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1);
            case "no2_strata_zip_not_middle_no_indicator": return loadCleanDSStrataSESFeature_no_indicator(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2);
            case "no2_strata_zip_not_rich_no_indicator": return loadCleanDSStrataSESFeature_no_indicator(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3);

            //  Mortality ~ No2 + (Not-low-income_ZIP * No2) + SES_state.
            case "no2_strata_zip_not_poor_state_no_indicator": return loadCleanDSStrataSESFeatureState_no_indicator(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 1);
            case "no2_strata_zip_not_middle_state_no_indicator": return loadCleanDSStrataSESFeatureState_no_indicator(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 2);
            case "no2_strata_zip_not_rich_state_no_indicator": return loadCleanDSStrataSESFeatureState_no_indicator(input, 9, new int[]{0,2,3,4}, new double[]{0.617,0.961}, 3);

            case "dump_features": dumpFeatures(input, 7, 9); return null;

            // updates to all years.
            // low
            case "pm_strata_zip_gross_l": return loadCleanDSStrataGrossFiliter(input, 7, 0, 0, new double[]{0.617, 0.961});
            // middle
            case "pm_strata_zip_gross_m": return loadCleanDSStrataGrossFiliter(input, 7, 0, 1, new double[]{0.617, 0.961});
            // high
            case "pm_strata_zip_gross_h": return loadCleanDSStrataGrossFiliter(input, 7, 0, 2, new double[]{0.617, 0.961});
            case "pm_strata_zip_gross_feature_unnorm_in_range": return loadCleanDSStrataGrossFeatureUnNormRange(input, 7, 0, 5, 16); // pm+unnormalized_zip + state between 5 to 16
            // feature index as first feature, then zip_level SES data, and then state average SES data.
            case "pm_strata_zip_gross_feature_nostrata_zip": return loadCleanDSStrataGrossFeature(input, 7, 2); // pm+normailzied_zip+state // test no strata on zip.
            case "pm_strata_zip_gross_feature": return loadCleanDSStrataGrossFeature(input, 7, 0); // pm+normailzied_zip+state
            case "pm_strata_zip_gross_feature_unnorm": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 0); // pm+unnormalized_zip + state
            case "pm_strata_zip_gross_feature_unnorm_monitor_site": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 0, "/scratch/wang.bin/health/DSenrollee/original/pm25_bz6_2008.csv"); // pm+unnormalized_zip + state
            case "pm_strata_zip_gross_feature_unnorm_male": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 2, "1", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_female": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 2, "2", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_white": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 3, "1", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_nonwhite": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 3, "1", false, 0);
            case "pm_strata_zip_gross_feature_unnorm_black": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 3, "2", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_asian": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 3, "4", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_hispanic": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 3, "5", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_urban": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 11, "1", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_suburban": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 11, "2", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_rural": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 11, "3", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_nonurban": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 11, "1", false, 0);
            case "pm_strata_zip_gross_feature_unnorm_west": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 12, "3", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_northeast": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 12, "2", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_midwest": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 12, "1", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_south": return loadCleanDSStrataGrossFeatureUnNorm(input, 7, 12, "0", true, 0);
            case "pm_strata_zip_gross_feature_unnorm_less75": return loadCleanDSStrataGrossFeatureUnNormLEQAgeStrata(input, 7, 4, 75, 0);
            case "pm_strata_zip_gross_feature_unnorm_more75": return loadCleanDSStrataGrossFeatureUnNormLargerAgeStrata(input, 7, 4, 75, 0);

            case "pm_strata_zip_ses_state": return loadCleanDSStrataSESState(input, 7, 0); // pm + ses_state
            case "pm_strata_zip_ses_state_male": return loadCleanDSStrataSESState(input, 7, 2, "1", true, 0);
            case "pm_strata_zip_ses_state_female": return loadCleanDSStrataSESState(input, 7, 2, "2", true, 0);
            case "pm_strata_zip_ses_state_white": return loadCleanDSStrataSESState(input, 7, 3, "1", true, 0);
            case "pm_strata_zip_ses_state_nonwhite": return loadCleanDSStrataSESState(input, 7, 3, "1", false, 0);
            case "pm_strata_zip_ses_state_black": return loadCleanDSStrataSESState(input, 7, 3, "2", true, 0);
            case "pm_strata_zip_ses_state_asian": return loadCleanDSStrataSESState(input, 7, 3, "4", true, 0);
            case "pm_strata_zip_ses_state_hispanic": return loadCleanDSStrataSESState(input, 7, 3, "5", true, 0);
            case "pm_strata_zip_ses_state_urban": return loadCleanDSStrataSESState(input, 7, 11, "1", true, 0);
            case "pm_strata_zip_ses_state_suburban": return loadCleanDSStrataSESState(input, 7, 11, "2", true, 0);
            case "pm_strata_zip_ses_state_rural": return loadCleanDSStrataSESState(input, 7, 11, "3", true, 0);
            case "pm_strata_zip_ses_state_nonurban": return loadCleanDSStrataSESState(input, 7, 11, "1", false, 0);
            case "pm_strata_zip_ses_state_west": return loadCleanDSStrataSESState(input, 7, 12, "3", true, 0);
            case "pm_strata_zip_ses_state_northeast": return loadCleanDSStrataSESState(input, 7, 12, "2", true, 0);
            case "pm_strata_zip_ses_state_midwest": return loadCleanDSStrataSESState(input, 7, 12, "1", true, 0);
            case "pm_strata_zip_ses_state_south": return loadCleanDSStrataSESState(input, 7, 12, "0", true, 0);

            case "pm_strata_zip_ses_state_less75": return loadCleanDSStrataSESStateLEQAgeStrata(input, 7, 4, 75, 0);
            case "pm_strata_zip_ses_state_more75": return loadCleanDSStrataSESStateLargerAgeStrata(input, 7, 4, 75, 0);


            case "pm_strata_zip_gross_feature_unnorm_zip": return loadCleanDSStrataGrossFeatureUnNormZip(input, 7, 0); // pm+unnormalized_zip

//            case "pm_strata_zip_gross_l_urban": return loadCleanDSStrataGrossFiliter(input, 7, 0, 0, new double[]{0.617, 0.961},  11, "1");
//            case "pm_strata_zip_gross_m_urban": return loadCleanDSStrataGrossFiliter(input, 7, 0, 1, new double[]{0.617, 0.961},  11, "1");
//            case "pm_strata_zip_gross_h_urban": return loadCleanDSStrataGrossFiliter(input, 7, 0, 2, new double[]{0.617, 0.961},  11, "1");

//            case "pm_strata_zip_gross_fea": return loadCleanDSStrataGross(input, 7, 0, 8, 9);
//
//            case "no2_strata_zip_gross": return loadCleanDSStrataGross(input, 9, 0);
//            case "no2_strata_zip_gross_low": return loadCleanDSStrataGrossFiliter(input, 9, 0, 0.778, true);
//            case "no2_strata_zip_gross_high": return loadCleanDSStrataGrossFiliter(input, 9, 0, 0.778, false);

            // low
            case "no2_strata_zip_gross_l": return loadCleanDSStrataGrossFiliter(input, 9, 0, 0, new double[]{0.617, 0.961});
            // middle
            case "no2_strata_zip_gross_m": return loadCleanDSStrataGrossFiliter(input, 9, 0, 1, new double[]{0.617, 0.961});
            // high
            case "no2_strata_zip_gross_h": return loadCleanDSStrataGrossFiliter(input, 9, 0, 2, new double[]{0.617, 0.961});
            case "no2_strata_zip_gross_feature": return loadCleanDSStrataGrossFeature(input, 9, 0);
            case "no2_strata_zip_gross_feature_unnorm": return loadCleanDSStrataGrossFeatureUnNorm(input, 9,  0); // pm+unnormalized_zip + state
            case "no2_strata_zip_gross_feature_unnorm_male": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 2, "1", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_female": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 2, "2", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_white": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 3, "1", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_nonwhite": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 3, "1", false, 0);
            case "no2_strata_zip_gross_feature_unnorm_black": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 3, "2", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_asian": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 3, "4", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_hispanic": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 3, "5", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_urban": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 11, "1", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_suburban": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 11, "2", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_rural": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 11, "3", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_nonurban": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 11, "1", false, 0);
            case "no2_strata_zip_gross_feature_unnorm_west": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 12, "3", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_northeast": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 12, "2", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_midwest": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 12, "1", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_south": return loadCleanDSStrataGrossFeatureUnNorm(input, 9, 12, "0", true, 0);
            case "no2_strata_zip_gross_feature_unnorm_less75": return loadCleanDSStrataGrossFeatureUnNormLEQAgeStrata(input, 9, 4, 75, 0);
            case "no2_strata_zip_gross_feature_unnorm_more75": return loadCleanDSStrataGrossFeatureUnNormLargerAgeStrata(input, 9, 4, 75, 0);

            case "no2_strata_zip_gross_feature_unnorm_zip": return loadCleanDSStrataGrossFeatureUnNormZip(input, 9, 0); // pm+unnormalized_zip

            case "no2_strata_zip_gross_feature_unnorm_low_15": return loadCleanDSStrataGrossFeatureUnNorm(input, 9,  0, "/scratch/wang.bin/health/DSenrollee/final/averageNo2.csv", 15); // pm+unnormalized_zip + state

//            case "no2_strata_zip_gross_l_urban": return loadCleanDSStrataGrossFiliter(input, 9, 0, 0, new double[]{0.625, 0.942},  11, "1");
//            case "no2_strata_zip_gross_m_urban": return loadCleanDSStrataGrossFiliter(input, 9, 0, 1, new double[]{0.625, 0.942},  11, "1");
//            case "no2_strata_zip_gross_h_urban": return loadCleanDSStrataGrossFiliter(input, 9, 0, 2, new double[]{0.625, 0.942},  11, "1");


//            case "no2_strata_zip_gross_fea": return loadCleanDSStrataGross(input, 9, 0, 8, 9);

            /**
             * New analyses for PM
             */
            case "pm_strata_zip_white_urban": return loadCleanDS(input, 7, 3, "1", true,  11, "1",  0);
            case "pm_strata_zip_nonwhite_urban": return loadCleanDS(input, 7, 3, "1", false, 11, "1", 0);

            case "pm_strata_zip_urban_west": return loadCleanDS(input, 7, 11, "1", true, 12, "3", 0);
            case "pm_strata_zip_urban_northeast": return loadCleanDS(input, 7, 11, "1", true, 12, "2", 0);
            case "pm_strata_zip_urban_midwest": return loadCleanDS(input, 7, 11, "1", true, 12, "1",0);
            case "pm_strata_zip_urban_south": return loadCleanDS(input, 7, 11, "1", true, 12, "0",0);

            case "pm_strata_zip_nonurban_west": return loadCleanDS(input, 7, 11, "1", false, 12, "3", 0);
            case "pm_strata_zip_nonurban_northeast": return loadCleanDS(input, 7, 11, "1", false, 12, "2", 0);
            case "pm_strata_zip_nonurban_midwest": return loadCleanDS(input, 7, 11, "1", false, 12, "1",0);
            case "pm_strata_zip_nonurban_south": return loadCleanDS(input, 7, 11, "1", false, 12, "0",0);


            case "no2_strata_zip": return loadCleanDSStrata(input, 9, 0);
            case "no2_strata_zip_male": return loadCleanDS(input, 9, 2, "1", true, 0);
            case "no2_strata_zip_female": return loadCleanDS(input, 9, 2, "2", true, 0);
            case "no2_strata_zip_white": return loadCleanDS(input, 9, 3, "1", true, 0);
            case "no2_strata_zip_black": return loadCleanDS(input, 9, 3, "2", true, 0);
            case "no2_strata_zip_asian": return loadCleanDS(input, 9, 3, "4", true, 0);
            case "no2_strata_zip_hispanic": return loadCleanDS(input, 9, 3, "5", true, 0);
            case "no2_strata_zip_nonwhite": return loadCleanDS(input, 9, 3, "1", false, 0);
            case "no2_strata_zip_urban": return loadCleanDS(input, 9, 11, "1", true, 0);
            case "no2_strata_zip_suburban": return loadCleanDS(input, 9, 11, "2", true, 0);
            case "no2_strata_zip_rural": return loadCleanDS(input, 9, 11, "3", true, 0);
            case "no2_strata_zip_nonurban": return loadCleanDS(input, 9, 11, "1", false, 0);
            case "no2_strata_zip_west": return loadCleanDS(input, 9, 12, "3", true, 0);
            case "no2_strata_zip_northeast": return loadCleanDS(input, 9, 12, "2", true, 0);
            case "no2_strata_zip_midwest": return loadCleanDS(input, 9, 12, "1", true, 0);
            case "no2_strata_zip_south": return loadCleanDS(input, 9, 12, "0", true, 0);
            case "no2_strata_zip_less75": return loadCleanDSLEQAgeStrata(input, 9, 75, 0);
            case "no2_strata_zip_more75": return loadCleanDSLargerAgeStrata(input, 9, 75, 0);

            case "no2_strata_zip_urban_usda": return loadCleanDSUSDA(input, 9, 0, "/scratch/wang.bin/health/DSenrollee/final_1/urbanCode.csv", 3, true);
            case "no2_strata_zip_nonurban_usda": return loadCleanDSUSDA(input, 9, 0, "/scratch/wang.bin/health/DSenrollee/final_1/urbanCode.csv", 3, false);


            case "pmc_strata_state": return loadCleanDSStrata(input, 8, 13);
            case "pmc_strata_state_male": return loadCleanDS(input, 8, 2, "1", true, 13);
            case "pmc_strata_state_female": return loadCleanDS(input, 8, 2, "2", true, 13);
            case "pmc_strata_state_white": return loadCleanDS(input, 8, 3, "1", true, 13);
            case "pmc_strata_state_nonwhite": return loadCleanDS(input, 8, 3, "1", false, 13);
            case "pmc_strata_state_urban": return loadCleanDS(input, 8, 11, "1", true, 13);
            case "pmc_strata_state_suburban": return loadCleanDS(input, 8, 11, "2", true, 13);
            case "pmc_strata_state_rural": return loadCleanDS(input, 8, 11, "3", true, 13);
            case "pmc_strata_state_nonurban": return loadCleanDS(input, 8, 11, "1", false, 13);
            case "pmc_strata_state_west": return loadCleanDS(input, 8, 12, "3", true, 13);
            case "pmc_strata_state_northeast": return loadCleanDS(input, 8, 12, "2", true, 13);
            case "pmc_strata_state_midwest": return loadCleanDS(input, 8, 12, "1", true, 13);
            case "pmc_strata_state_south": return loadCleanDS(input, 8, 12, "0", true, 13);
            case "pmc_strata_state_less75": return loadCleanDSLEQAgeStrata(input, 8, 75, 13);
            case "pmc_strata_state_more75": return loadCleanDSLargerAgeStrata(input, 8, 75, 13);
            // ------ Strata on another exposure
            case "pmc_strata_no2_12": return loadCleanDSStrataExtra(input, 8, 13, 9, 12);
            case "pmc_strata_no2_10": return loadCleanDSStrataExtra(input, 8, 13, 9, 10);
            case "no2c_strata_pm_10": return loadCleanDSStrataExtra(input, 10, 13, 7, 10);
            case "no2c_strata_pm_8": return loadCleanDSStrataExtra(input, 10, 13, 7, 8);


            // -------strata on age only-----------
            case "pmc_northeast_strataAge": return loadCleanDSStrataAge(input, 8, 12, "NORTHEAST", true);
            case "pmc_midwest_strataAge": return loadCleanDSStrataAge(input, 8, 12, "MIDWEST", true);
            case "pmc_south_strataAge": return loadCleanDSStrataAge(input, 8, 12, "SOUTH", true);
            case "pmc_west_strataAge": return loadCleanDSStrataAge(input, 8, 12, "WEST", true);
            case "pmc_northeast_noStrata": return loadCleanDSNoStrata(input, 8, 12, "NORTHEAST", true);
            case "pmc_midwest_noStrata": return loadCleanDSNoStrata(input, 8, 12, "MIDWEST", true);
            case "pmc_south_noStrata": return loadCleanDSNoStrata(input, 8, 12, "SOUTH", true);
            case "pmc_west_noStrata": return loadCleanDSNoStrata(input, 8, 12, "WEST", true);

            // ************* NO2 *********************
            case "no2_male": return loadCleanDS(input, 9, 2, "1", true);
            case "no2_female": return loadCleanDS(input, 9, 2, "2", true);
            case "no2_white": return loadCleanDS(input, 9, 3, "1", true);
            case "no2_nonwhite": return loadCleanDS(input, 9, 3, "1", false);
            case "no2_urban": return loadCleanDS(input, 9, 11, "1", true);
            case "no2_nonurban": return loadCleanDS(input, 9, 11, "1", false);
            case "no2_west": return loadCleanDS(input, 9, 12, "3", true);
            case "no2_northeast": return loadCleanDS(input, 9, 12, "2", true);
            case "no2_midwest": return loadCleanDS(input, 9, 12, "1", true);
            case "no2_south": return loadCleanDS(input, 9, 12, "0", true);
            case "no2_less75": return loadCleanDSLEQAge(input, 9, 75);
            case "no2_more75": return loadCleanDSLargerAge(input, 9, 75);

            // ************* NO2C *********************
            case "no2c_male": return loadCleanDS(input, 10, 2, "1", true);
            case "no2c_female": return loadCleanDS(input, 10, 2, "2", true);
            case "no2c_white": return loadCleanDS(input, 10, 3, "1", true);
            case "no2c_nonwhite": return loadCleanDS(input, 10, 3, "1", false);
            case "no2c_urban": return loadCleanDS(input, 10, 11, "1", true);
            case "no2c_nonurban": return loadCleanDS(input, 10, 11, "1", false);
            case "no2c_west": return loadCleanDS(input, 10, 12, "3", true);
            case "no2c_northeast": return loadCleanDS(input, 10, 12, "2", true);
            case "no2c_midwest": return loadCleanDS(input, 10, 12, "1", true);
            case "no2c_south": return loadCleanDS(input, 10, 12, "0", true);
            case "no2c_less75": return loadCleanDSLEQAge(input, 10, 75);
            case "no2c_more75": return loadCleanDSLargerAge(input, 10, 75);
            // ************** NO2C + Area *******************
            case "no2c_area_male": return loadCleanDSArea(input, 10, 2, "1", true);
            case "no2c_area_female": return loadCleanDSArea(input, 10, 2, "2", true);
            case "no2c_area_white": return loadCleanDSArea(input, 10, 3, "1", true);
            case "no2c_area_nonwhite": return loadCleanDSArea(input, 10, 3, "1", false);
            case "no2c_area_urban": return loadCleanDSArea(input, 10, 11, "1", true);
            case "no2c_area_nonurban": return loadCleanDSArea(input, 10, 11, "1", false);
            case "no2c_area_west": return loadCleanDSArea(input, 10, 12, "3", true);
            case "no2c_area_northeast": return loadCleanDSArea(input, 10, 12, "2", true);
            case "no2c_area_midwest": return loadCleanDSArea(input, 10, 12, "1", true);
            case "no2c_area_south": return loadCleanDSArea(input, 10, 12, "0", true);
            case "no2c_area_less75": return loadCleanDSLEQAgeArea(input, 10, 75);
            case "no2c_area_more75": return loadCleanDSLargerAgeArea(input, 10, 75);
            // -------strata on state-------------
            case "no2_strata_state": return loadCleanDSStrata(input, 9, 13);
            case "no2_strata_state_threshold_16": return loadCleanDSStrata(input, 9, 13, 9, 16.0);
            case "no2_strata_state_threshold_12": return loadCleanDSStrata(input, 9, 13, 9, 12.0);

            case "no2c_strata_state": return loadCleanDSStrata(input, 10, 13);
            case "no2c_strata_state_male": return loadCleanDS(input, 10, 2, "1", true, 13);
            case "no2c_strata_state_female": return loadCleanDS(input, 10, 2, "2", true, 13);
            case "no2c_strata_state_white": return loadCleanDS(input, 10, 3, "1", true, 13);
            case "no2c_strata_state_nonwhite": return loadCleanDS(input, 10, 3, "1", false, 13);
            case "no2c_strata_state_urban": return loadCleanDS(input, 10, 11, "1", true, 13);
            case "no2c_strata_state_suburban": return loadCleanDS(input, 10, 11, "2", true, 13);
            case "no2c_strata_state_rural": return loadCleanDS(input, 10, 11, "3", true, 13);
            case "no2c_strata_state_nonurban": return loadCleanDS(input, 10, 11, "1", false, 13);
            case "no2c_strata_state_west": return loadCleanDS(input, 10, 12, "3", true, 13);
            case "no2c_strata_state_northeast": return loadCleanDS(input, 10, 12, "2", true, 13);
            case "no2c_strata_state_midwest": return loadCleanDS(input, 10, 12, "1", true, 13);
            case "no2c_strata_state_south": return loadCleanDS(input, 10, 12, "0", true, 13);
            case "no2c_strata_state_less75": return loadCleanDSLEQAgeStrata(input, 10, 75, 13);
            case "no2c_strata_state_more75": return loadCleanDSLargerAgeStrata(input, 10, 75, 13);

            default: throw new RuntimeException("group: " + group + " is not supported yet!");
        }
    }

    private static DataFormat loadCleanDSNLargerAge(String input, int featureIndex1, int featureIndex2, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt <= threashold){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));
            vector.set(2, vector.get(0)*vector.get(1));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSLargerAgeArea(String input, int featureIndex1, int featureIndex2, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt <= threashold){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(6);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));
            String area = lineInfo[12];
            if (area.equals("")) {
                continue;
            }
            switch (area) {
                case "0": vector.setQuick(2, 1);
                    break;
                case "1": vector.setQuick(3, 1);
                    break;
                case "2": vector.setQuick(4, 1);
                    break;
                case "3": vector.setQuick(5,1);
                    break;
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(sex);
            key.setRace(race);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(6);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSLargerAge(String input, int featureIndex1, int featureIndex2, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt <= threashold){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * larger than the given age.
     * @param input
     * @param featureIndex
     * @param threashold
     * @return
     * @throws IOException
     */
    private static DataFormat loadCleanDSLargerAgeStrata(String input, int featureIndex, int threashold, int strataFeature) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt <= threashold){
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            if (Integer.valueOf(lineInfo[1]) >= 1309) {
                continue;
            }
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * larger than the given age.
     * @param input
     * @param featureIndex
     * @param threashold
     * @return
     * @throws IOException
     */
    private static DataFormat loadCleanDSLargerAgeArea(String input, int featureIndex, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt <= threashold){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
//            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(5);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            String area = lineInfo[12];
            if (area.equals("")) {
                continue;
            }
            switch (area) {
                case "0": vector.setQuick(1, 1);
                    break;
                case "1": vector.setQuick(2, 1);
                    break;
                case "2": vector.setQuick(3, 1);
                    break;
                case "3": vector.setQuick(4,1);
                    break;
            }



            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(sex);
            key.setRace(race);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * larger than the given age.
     * @param input
     * @param featureIndex
     * @param threashold
     * @return
     * @throws IOException
     */
    private static DataFormat loadCleanDSLargerAge(String input, int featureIndex, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt <= threashold){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSNLEQAge(String input, int featureIndex1, int featureIndex2, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt > threashold){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));
            vector.set(2, vector.get(0)*vector.get(1));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSLEQAgeArea(String input, int featureIndex1, int featureIndex2, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt > threashold){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(6);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));
            String area = lineInfo[12];
            if (area.equals("")) {
                continue;
            }
            switch (area) {
                case "0": vector.setQuick(2, 1);
                    break;
                case "1": vector.setQuick(3, 1);
                    break;
                case "2": vector.setQuick(4, 1);
                    break;
                case "3": vector.setQuick(5,1);
                    break;
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(sex);
            key.setRace(race);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(6);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSLEQAge(String input, int featureIndex1, int featureIndex2, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt > threashold){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSLEQAgeStrata(String input, int featureIndex, int threashold, int strataFeature) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt > threashold){
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            if (Integer.valueOf(lineInfo[1]) >= 1309) {
                continue;
            }
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * less or equal to the given age
     * @param input
     * @param featureIndex
     * @param threashold
     * @return
     * @throws IOException
     */
    private static DataFormat loadCleanDSLEQAgeArea(String input, int featureIndex, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt > 75){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
//            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(5);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            String area = lineInfo[12];
            if (area.equals("")) {
                continue;
            }
            switch (area) {
                case "0": vector.setQuick(1, 1);
                    break;
                case "1": vector.setQuick(2, 1);
                    break;
                case "2": vector.setQuick(3, 1);
                    break;
                case "3": vector.setQuick(4,1);
                    break;
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * less or equal to the given age
     * @param input
     * @param featureIndex
     * @param threashold
     * @return
     * @throws IOException
     */
    private static DataFormat loadCleanDSLEQAge(String input, int featureIndex, int threashold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            int ageInt = Integer.parseInt(lineInfo[4]);
            if (ageInt > 75){
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];

            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load the cleaned DS data by strata only by age, and use the given featureIndex value as the exposure.
     * and use the filterIndex as the filter;
     * ifEqual is ture, then filter has to satisfy the equation; otherwise not satisfy.
     * @param input
     * @param featureIndex
     * @param filterIndex
     * @param filterValue
     * @return
     */
    private static DataFormat loadCleanDSNoStrata(String input, int featureIndex,
                                                  int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            // parse key
            String date = lineInfo[1];
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load the cleaned DS data by strata only by age, and use the given featureIndex value as the exposure.
     * and use the filterIndex as the filter;
     * ifEqual is ture, then filter has to satisfy the equation; otherwise not satisfy.
     * @param input
     * @param featureIndex
     * @param filterIndex
     * @param filterValue
     * @return
     */
    private static DataFormat loadCleanDSStrataAge(String input, int featureIndex,
                                                   int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            // parse key
            String date = lineInfo[1];
            String age = lineInfo[4];

            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(age);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load two features, and using the product of these two features as the third feature.
     * load the cleaned DS data, and use the given featureIndex value as the exposure.
     * and use the filterIndex as the filter;
     * ifEqual is ture, then filter has to satisfy the equation; otherwise not satisfy.
     * @param input
     * @param featureIndex1
     * @param featureIndex2
     * @param filterIndex
     * @param filterValue
     * @return
     */
    private static DataFormat loadCleanDSN(String input, int featureIndex1, int featureIndex2,
                                           int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));
            vector.set(2, vector.get(0)*vector.get(1));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDS(String input, int featureIndex,
                                          int filterIndex, String filterValue, boolean ifEqual,
                                          int filterIndex_1, String filterValue_1, int strataFeature) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            if (!lineInfo[filterIndex_1].equals(filterValue_1)) {
                continue;
            }
            // parse key
//            String date = lineInfo[1];
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSUSDA(String input, int featureIndex,
                                              int strataFeature, String usdaFile, int threshold, boolean isLess) throws IOException {
        Map<String, Integer> usdaMap = loadUSDA(usdaFile);
        System.out.println("USDA #zipcodes loaded: " + usdaMap.size());
        Set<String> usedSet = new HashSet<>();
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String zip = lineInfo[0];
            int code = usdaMap.getOrDefault(zip, -1);
            if (code == -1) continue;

            if (isLess) {
                if (code > threshold) {
                    continue;
                }
                usedSet.add(zip);
            } else {
                if (code <= threshold) {
                    continue;
                }
                usedSet.add(zip);
            }
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }

            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        System.out.println("#zipcodes has been used: " + usedSet.size());

        return data;
    }


    private static DataFormat loadCleanDS(String input, int featureIndex,
                                          int filterIndex, String filterValue, boolean ifEqual, int strataFeature) throws IOException {
        System.out.println("Year to 2008");
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            // parse key
            // date before 2009,Jan: 2000 - 2008
//            if (Integer.valueOf(lineInfo[1]) >= 1309) {
//                continue;
//            }
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSArea(String input, int featureIndex1, int featureIndex2,
                                              int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            // parse key
//            String date = lineInfo[1];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(6);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));
            String area = lineInfo[12];
            if (area.equals("")) {
                continue;
            }
            switch (area) {
                case "0": vector.setQuick(2, 1);
                    break;
                case "1": vector.setQuick(3, 1);
                    break;
                case "2": vector.setQuick(4, 1);
                    break;
                case "3": vector.setQuick(5,1);
                    break;
            }


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(age);
            key.setSex(sex);
            key.setRace(race);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(6);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * load two features
     * load the cleaned DS data, and use the given featureIndex value as the exposure.
     * and use the filterIndex as the filter;
     * ifEqual is ture, then filter has to satisfy the equation; otherwise not satisfy.
     * @param input
     * @param featureIndex1
     * @param featureIndex2
     * @param filterIndex
     * @param filterValue
     * @return
     */
    private static DataFormat loadCleanDS(String input, int featureIndex1, int featureIndex2,
                                          int filterIndex, String filterValue, boolean ifEqual, String filter) throws IOException {
        Set<String> filterZip = loadFilterMonitor(filter);
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            if (!filterZip.contains(lineInfo[0])) {
                continue;
            }
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSResidual_urban(String input, int featureIndex1, int featureIndex2,
                                                        int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        Map<String, Double> betaMap = new HashMap<>();
        Map<String, Double> biasMap = new HashMap<>();
        switch (filterValue) {
            case "3":
                betaMap.put("1", 1.265); //west_urban
                biasMap.put("1", 3.772);
                betaMap.put("2", 0.4868); //west_nonurban
                biasMap.put("2", 4.1272);
                break;
            case "2": //northest
                betaMap.put("1", 2.054);
                biasMap.put("1", -6.327);
                betaMap.put("2", 0.7963);
                biasMap.put("2", 1.3082);
                break;
            case "1":// midwest_urban
                betaMap.put("1", 1.497);
                biasMap.put("1", -5.224);
                betaMap.put("2", 0.5799);
                biasMap.put("2", 0.9215);
                break;
            case "0":// south_urban
                betaMap.put("1", 0.9533);
                biasMap.put("1", -0.8093);
                betaMap.put("2", 0.3776);
                biasMap.put("2", 2.0415);

        }
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            String urban = lineInfo[11];
            double beta;
            double bias;
            if (urban == "1") {
                beta = betaMap.get("1");
                bias = biasMap.get("1");
            } else {
                beta = betaMap.get("2");
                bias = biasMap.get("2");
            }

            // set feature value
            DenseVector vector = new DenseVector(1);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, no2-beta*pm-bias);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidual_2_urban(String input, int featureIndex1, int featureIndex2,
                                                          int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        Map<String, Double> betaMap = new HashMap<>();
        Map<String, Double> biasMap = new HashMap<>();
        switch (filterValue) {
            case "3":
                betaMap.put("1", 0.4842); //west_urban
                biasMap.put("1", 2.0142);
                betaMap.put("2",  0.5377); //west_nonurban
                biasMap.put("2",1.3752);
                break;
            case "2": //northest
                betaMap.put("1", 0.2514);
                biasMap.put("1", 6.7537);
                betaMap.put("2", 0.5426);
                biasMap.put("2", 3.6073);
                break;
            case "1":// midwest_urban
                betaMap.put("1", 0.3229);
                biasMap.put("1",7.5410);
                betaMap.put("2",  0.6539);
                biasMap.put("2", 4.5503);
                break;
            case "0":// south_urban
                betaMap.put("1",  0.3558);
                biasMap.put("1", 7.2190);
                betaMap.put("2", 0.3645);
                biasMap.put("2", 7.2912);

        }
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            String urban = lineInfo[11];
            double beta;
            double bias;
            if (urban == "1") {
                beta = betaMap.get("1");
                bias = biasMap.get("1");
            } else {
                beta = betaMap.get("2");
                bias = biasMap.get("2");
            }

            // set feature value
            DenseVector vector = new DenseVector(1);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, no2-beta*pm-bias);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSResidual_2_area(String input, int featureIndex1, int featureIndex2,
                                                         int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        Map<String, Double> betaMap = new HashMap<>();
        Map<String, Double> biasMap = new HashMap<>();
        // urban
        if (ifEqual) {
            // west_urban
            betaMap.put("3", 0.4842);
            biasMap.put("3", 2.0142);
            // northeast_urban
            betaMap.put("2", 0.2514);
            biasMap.put("2", 6.7537);
            // midwest_urban
            betaMap.put("1", 0.3229);
            biasMap.put("1", 7.5410);
            // south_urban
            betaMap.put("0", 0.3558);
            biasMap.put("0", 7.2190);
        } else { // nonurban
            // west_nonurban
            betaMap.put("3", 0.5377);
            biasMap.put("3", 1.3752);
            // northeast_urban
            betaMap.put("2", 0.5426);
            biasMap.put("2", 3.6073);
            // midwest_urban
            betaMap.put("1", 0.6539);
            biasMap.put("1", 4.5503);
            // south_urban
            betaMap.put("0", 0.3645);
            biasMap.put("0", 7.2912);
        }
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            String area = lineInfo[12];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            double beta = betaMap.get(area);
            double bias = biasMap.get(area);
            // set feature value
            DenseVector vector = new DenseVector(1);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, no2-beta*pm-bias);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidual_area(String input, int featureIndex1, int featureIndex2,
                                                       int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        Map<String, Double> betaMap = new HashMap<>();
        Map<String, Double> biasMap = new HashMap<>();
        // urban
        if (ifEqual) {
            // west_urban
            betaMap.put("3", 1.265);
            biasMap.put("3", 3.772);
            // northeast_urban
            betaMap.put("2", 2.054);
            biasMap.put("2", -6.327);
            // midwest_urban
            betaMap.put("1", 1.497);
            biasMap.put("1", -5.224);
            // south_urban
            betaMap.put("0", 0.9533);
            biasMap.put("0", -0.8093);
        } else { // nonurban
            // west_nonurban
            betaMap.put("3", 0.4868);
            biasMap.put("3", 4.1272);
            // northeast_urban
            betaMap.put("2", 0.7963);
            biasMap.put("2", 1.3082);
            // midwest_urban
            betaMap.put("1", 0.5799);
            biasMap.put("1", 0.9215);
            // south_urban
            betaMap.put("0", 0.3776);
            biasMap.put("0", 2.0415);
        }
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            String area = lineInfo[12];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            double beta = betaMap.get(area);
            double bias = biasMap.get(area);
            // set feature value
            DenseVector vector = new DenseVector(1);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, no2-beta*pm-bias);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidual(String input, int featureIndex1, int featureIndex2, double beta, double bias,
                                                  int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") ||
                    lineInfo[featureIndex2].equals("NA")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, no2-beta*pm-bias);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * load two features
     * load the cleaned DS data, and use the given featureIndex value as the exposure.
     * and use the filterIndex as the filter;
     * ifEqual is ture, then filter has to satisfy the equation; otherwise not satisfy.
     * @param input
     * @param featureIndex1
     * @param featureIndex2
     * @param filterIndex
     * @param filterValue
     * @return
     */
    private static DataFormat loadCleanDS(String input, int featureIndex1, int featureIndex2,
                                          int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }


    /**
     * load the cleaned DS data, and use the given featureIndex value as the exposure.
     * and use the filterIndex as the filter;
     * ifEqual is ture, then filter has to satisfy the equation; otherwise not satisfy.
     * @param input
     * @param featureIndex
     * @param filterIndex
     * @param filterValue
     * @return
     */
    private static DataFormat loadCleanDSArea(String input, int featureIndex,
                                              int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            // parse key
//            String date = lineInfo[1];
//            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(5);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            String area = lineInfo[12];
            if (area.equals("")) {
                continue;
            }
            switch (area) {
                case "0": vector.setQuick(1, 1);
                    break;
                case "1": vector.setQuick(2, 1);
                    break;
                case "2": vector.setQuick(3, 1);
                    break;
                case "3": vector.setQuick(4,1);
                    break;
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(age);
            key.setSex(sex);
            key.setRace(race);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
        /**
         * load the cleaned DS data, and use the given featureIndex value as the exposure.
         * and use the filterIndex as the filter;
         * ifEqual is ture, then filter has to satisfy the equation; otherwise not satisfy.
         * @param input
         * @param featureIndex
         * @param filterIndex
         * @param filterValue
         * @return
         */
    private static DataFormat loadCleanDS(String input, int featureIndex,
                                          int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }
            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load cleaned DS data and using regions as one feature(1-4) too without any strata.
     * and use the given featureIndex value as the exposure.
     * @param input
     * @param featureIndex
     * @param dumpIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDSDumpNoStrata(String input, int featureIndex, int dumpIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") ||
                    lineInfo[dumpIndex].equals("")) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            // set feature value
            // regions as features too
            DenseVector vector = new DenseVector(2);
            vector.setQuick(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.setQuick(1, Double.parseDouble(lineInfo[dumpIndex]));
            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * load cleaned DS data and using regions as one feature(1-4) too without any strata.
     * and use the given featureIndex value as the exposure.
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDSRegionNoStrata(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") ||
                    lineInfo[12].equals("")) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            // set feature value
            // regions as features too
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            String region = lineInfo[12];
            switch (region.charAt(0)) {
                case 'S': vector.setQuick(1, 0);
                    break;
                case 'M': vector.setQuick(1, 1);
                    break;
                case 'N': vector.setQuick(1, 2);
                    break;
                case 'W': vector.setQuick(1, 3);
                    break;
                default: continue;
            }
            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load cleaned DS data and using regions as features too without any strata.
     * and use the given featureIndex value as the exposure.
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDSRegionsNoStrata(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") ||
                    lineInfo[12].equals("")) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            // set feature value
            // regions as features too
            DenseVector vector = new DenseVector(5);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            String region = lineInfo[12];
            switch (region.charAt(0)) {
                case 'S': vector.setQuick(1, 1);
                    break;
                case 'M': vector.setQuick(2, 1);
                    break;
                case 'N': vector.setQuick(3, 1);
                    break;
                case 'W': vector.setQuick(4, 1);
                    break;
                default: continue;
            }
            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * load cleaned DS data and using regions as features too strata by age only,
     * and use the given featureIndex value as the exposure.
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDSRegionsStrataAge(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") ||
                    lineInfo[12].equals("")) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            // set feature value
            // regions as features too
            DenseVector vector = new DenseVector(5);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            String region = lineInfo[12];
            switch (region.charAt(0)) {
                case 'S': vector.setQuick(1, 1);
                break;
                case 'M': vector.setQuick(2, 1);
                break;
                case 'N': vector.setQuick(3, 1);
                break;
                case 'W': vector.setQuick(4, 1);
                break;
                default: continue;
            }
            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(age);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSNoStrataNoDate(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * load cleaned DS data without strata, and use the given featureIndex value as the exposure.
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDSNoStrata(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * load cleaned DS data strata by age only, and use the given featureIndex value as the exposure.
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDSStrataAge(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            String age = lineInfo[4];
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(age);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load two features together and using the product of these two as the third feature.
     * @param input
     * @param featureIndex1
     * @param featureIndex2
     * @return
     */
    private static DataFormat loadCleanDSN(String input, int featureIndex1, int featureIndex2) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));
            vector.set(2, vector.get(0)*vector.get(1));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load two features together.
     * @param input
     * @param featureIndex1
     * @param featureIndex2
     * @return
     */
    private static DataFormat loadCleanDSArea(String input, int featureIndex1, int featureIndex2) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(6);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));
            String area = lineInfo[12];
            if (area.equals("")) {
                continue;
            }
            switch (area) {
                case "0": vector.setQuick(2, 1);
                    break;
                case "1": vector.setQuick(3, 1);
                    break;
                case "2": vector.setQuick(4, 1);
                    break;
                case "3": vector.setQuick(5,1);
                    break;
            }



            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(age);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(6);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * load two features together.
     * @param input
     * @param featureIndex1
     * @param featureIndex2
     * @return
     */
    private static DataFormat loadKiDoTwoFeatures(String input, int featureIndex1, int featureIndex2) throws IOException {
        System.out.println("kido two features data loading...");
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }

            // parse key
            String loc = lineInfo[5]; // site_id
            String ID = lineInfo[23];
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[10]);
            label[1] = Integer.parseInt(lineInfo[11]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc);
            key.setSex(ID);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
//    loadCleanDSWithScaling
private static DataFormat loadCleanDSWithScaling(String input, int featureIndex1, int featureIndex2) throws IOException {
    double mean1 = 10.150;
    double mean2 = 11.544;
    double std1 = 3.028;
    double std2 = 6.169;
    DataFormat data = new DataFormat();

    BufferedReader br = new BufferedReader(new FileReader(input));
    String line;
    int lineCount = -1;
    while ((line = br.readLine()) != null) {
        lineCount += 1;
        if (lineCount < 1) {
            continue;
        }
        String[] lineInfo = line.split(",", -1);
        // none exist for value
        if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
            continue;
        }

        // parse key
        String loc = lineInfo[0];  // zipcode
        String sex = lineInfo[2];
        String race = lineInfo[3];
        String age = lineInfo[4];
        int ageInt = Integer.parseInt(age);
        if (ageInt >= 90){
            age = "90";
        }
        // set feature value
        DenseVector vector = new DenseVector(2);
        vector.set(0, (Double.parseDouble(lineInfo[featureIndex1])-mean1)/std1);
        vector.set(1, (Double.parseDouble(lineInfo[featureIndex2])-mean2)/std2);

        // parse label: life and death
        int[] label = new int[2];
        label[0] = Integer.parseInt(lineInfo[5]);
        label[1] = Integer.parseInt(lineInfo[6]);
        if (label[0] == 0) {
            continue;
        }

        // insert row data
        MultiKey key = new MultiKey(loc, age, race, sex);
        key.setSingleKey(false);
        Pair<int[], Vector> pair = new Pair<>(label, vector);
        data.insertRowData(key, pair);
    }

    br.close();
    data.setNumFeatures(2);
    data.setNumStrata(data.getData().size());

    return data;
}

    private static DataFormat loadCleanDSResidualWithSESUnNormZip(String input, int featureIndex1, int featureIndex2, double beta, double bias) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, Double>> gross = loadGrossFeatureUnNormZip("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]

            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, pm - beta * no2 - bias); // residual between pm and no2
            vector.set(1, grossValue);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidualWithSESUnNormEqSmaller(String input, int featureIndex1, int featureIndex2, double beta, double bias,
                                                                        int filterIndex, double filterValue) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            double fValue = Double.parseDouble(lineInfo[filterIndex]);
            if (fValue > filterValue) {
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]
            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, pm - beta * no2 - bias); // residual between pm and no2
            vector.set(1, grossValue[0]);
            vector.set(2, grossValue[1]);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidualWithSESUnNormLarger(String input, int featureIndex1, int featureIndex2, double beta, double bias,
                                                                     int filterIndex, double filterValue) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            double fValue = Double.parseDouble(lineInfo[filterIndex]);
            if (fValue <= filterValue) {
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]
            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, pm - beta * no2 - bias); // residual between pm and no2
            vector.set(1, grossValue[0]);
            vector.set(2, grossValue[1]);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidualWithSESUnNorm(String input, int featureIndex1, int featureIndex2, double beta, double bias,
                                                               int filterIndex, String filterValue, boolean ifEqual) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]
            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, pm - beta * no2 - bias); // residual between pm and no2
            vector.set(1, grossValue[0]);
            vector.set(2, grossValue[1]);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidualWithSESUnNorm(String input, int featureIndex1, int featureIndex2, double beta, double bias) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]

            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, pm - beta * no2 - bias); // residual between pm and no2
            vector.set(1, grossValue[0]);
            vector.set(2, grossValue[1]);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidualWithSES(String input, int featureIndex1, int featureIndex2, double beta, double bias) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]

            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, pm - beta * no2 - bias); // residual between pm and no2
            vector.set(1, grossValue[0]);
            vector.set(2, grossValue[1]);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSResidualSESOnly(String input, int featureIndex1, double beta1, double beta2, double bias) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]

            // none exist for value
            if (lineInfo[featureIndex1].equals("")  ||
                    lineInfo[featureIndex1].equals("NA")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            vector.set(0, pm-beta1*grossValue[0]-beta2*grossValue[1]-bias);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidualSES(String input, int featureIndex1, int featureIndex2, double beta1, double beta2, double beta3, double bias) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]

            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") ||
                    lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, no2-beta1*pm-beta2*grossValue[0]-beta3*grossValue[1]-bias);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidual(String input, int featureIndex1, int featureIndex2, double beta, double bias) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") ||
                    lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, no2-beta*pm-bias);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSWithPredBeta(String input, int featureIndex1, int featureIndex2, double beta, double bias) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            double predNo2 = bias + pm * beta;
            double residual = no2 - predNo2;

            vector.set(0, predNo2);
            vector.set(1, residual);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSWithBeta(String input, int featureIndex1, int featureIndex2, double beta, double bias) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, no2);
            double residual = no2 - bias - pm*beta;
            vector.set(1, residual);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSWithBeta(String input, int featureIndex1, int featureIndex2) throws IOException {
        double beta0 = -1.5914;
        double beta1 = 1.2599;
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }

            // parse key
            String loc = lineInfo[0];  // zipcodes
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            vector.set(0, pm);
            vector.set(1, no2);
            vector.set(2, no2-beta1*pm - beta0);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * load two features together.
     * @param input
     * @param featureIndex1
     * @param featureIndex2
     * @return
     */
    private static DataFormat loadCleanDS(String input, int featureIndex1, int featureIndex2) throws IOException {
//        System.out.println("single key loading...");
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];  // zipcodes
//            String loc = lineInfo[13]; // states: MA, CT
//            String loc = lineInfo[12]; // areas:west,south etc.
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
//            String keyString = (new StringBuilder()).append(date).append(loc).append(sex).append(race).append(age).toString();
//            MultiKey key = new MultiKey(keyString);
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
//            MultiKey key = new MultiKey(age);
//            key.setRace(race);
//            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static Set<String> loadFilterSite(String filter) throws IOException {
        Set<String> result = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(filter));
        int lineCount = -1;
        String line;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            result.add(lineInfo[0]);
        }
        return result;
    }


    private static Set<String> loadFilterMonitor(String filiter) throws IOException {
        Set<String> result = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(filiter));
        int lineCount = -1;
        String line;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            result.add(lineInfo[2]);
        }
        return result;
    }
    private static Set<String> loadFilterZip(String filiter) throws IOException {
        Set<String> result = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(filiter));
        int lineCount = -1;
        String line;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            result.add(lineInfo[1]);
        }
        return result;
    }
    private static Map<String, Map<String, Double>> loadFilter(String filter, int featureIndex) throws IOException {
        Set<String> sites = new HashSet<>();
        Map<String, Map<String, Double>> result = new HashMap<>();
        BufferedReader br = new BufferedReader((new FileReader(filter)));
        int lineCount = -1;
        String line;
        while ((line=br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) continue;
            String[] lineInfo = line.split(",", -1);
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            String site = lineInfo[0];
            double value = Double.parseDouble(lineInfo[featureIndex]);
            String zip = lineInfo[2];
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[3]);
            String date = String.valueOf((year-1900)*12 + month);
            if (!result.containsKey(zip)) {
                result.put(zip, new HashMap<>());
            }
            result.get(zip).put(date, value);
            sites.add(site);
        }
        System.out.println("#monitors: " + sites.size() + " are included.");
        return result;
    }
    private static DataFormat loadO3CenterByMonitor(String input, int featureIndex, String filter) throws IOException {
        Set<String> filterSite = loadFilterSite(filter);
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            if (!filterSite.contains(lineInfo[20])) {
                continue;
            }
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[4];

            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(ID);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load two features together.
     * @param input
     * @param featureIndex1
     * @param featureIndex2
     * @return
     */
    private static DataFormat loadCleanDSByMonitorNoStrataOnZip(String input, int featureIndex1, int featureIndex2, String filterFile) throws IOException {

        Set<String> filterZip = loadFilterMonitor(filterFile);
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            if (!filterZip.contains(lineInfo[0])) {
                continue;
            }
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }

            // parse key
//            String date = lineInfo[1];
//            String loc = lineInfo[0];  // zipcodes
//            String loc = lineInfo[13]; // states: MA, CT
//            String loc = lineInfo[12]; // areas:west,south etc.
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
//            String keyString = (new StringBuilder()).append(date).append(loc).append(sex).append(race).append(age).toString();
//            MultiKey key = new MultiKey(keyString);
            MultiKey key = new MultiKey(age);
            key.setSex(sex);
            key.setRace(race);
            key.setSingleKey(false);
//            MultiKey key = new MultiKey(age);
//            key.setRace(race);
//            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load two features together.
     * @param input
     * @param featureIndex1
     * @param featureIndex2
     * @return
     */
    private static DataFormat loadCleanDSByMonitor(String input, int featureIndex1, int featureIndex2, String filterFile) throws IOException {

        Set<String> filterZip = loadFilterMonitor(filterFile);
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            if (!filterZip.contains(lineInfo[0])) {
                continue;
            }
            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex2].equals("")) {
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];  // zipcodes
//            String loc = lineInfo[13]; // states: MA, CT
//            String loc = lineInfo[12]; // areas:west,south etc.
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex1]));
            vector.set(1, Double.parseDouble(lineInfo[featureIndex2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
//            String keyString = (new StringBuilder()).append(date).append(loc).append(sex).append(race).append(age).toString();
//            MultiKey key = new MultiKey(keyString);
            MultiKey key = new MultiKey(loc, age, race, sex);
            key.setSingleKey(false);
//            MultiKey key = new MultiKey(age);
//            key.setRace(race);
//            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * load cleaned DS data, and use the given featureIndex value as the exposure.
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDSByMonitorByFatemeh(String input, int featureIndex, String filiter) throws IOException {
        Set<String> filterZip = loadFilterMonitor(filiter);

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            if (!filterZip.contains(lineInfo[0])) {
                continue;
            }
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }

            // parse key
            String date = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
//            MultiKey key = new MultiKey(date);
//            key.setAge(age);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * load cleaned DS data, and use the given featureIndex value as the exposure.
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDSByMonitor(String input, int featureIndex, String filiter) throws IOException {
        Set<String> filterZip = loadFilterZip(filiter);

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            if (!filterZip.contains(lineInfo[0])) {
                continue;
            }
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
//            MultiKey key = new MultiKey(date);
//            key.setAge(age);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrata(String input, int featureIndex, int strataFeature, int thresholdFeature, double threshold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[thresholdFeature].equals("")) {
                continue;
            }
            double value = Double.parseDouble(lineInfo[thresholdFeature]);
            if (value > threshold) {
                continue;
            }
            // parse key
//            String date = lineInfo[1];
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataExtra(String input, int featureIndex, int strataFeature, int extraFeature,
                                                    double extraValue) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[extraFeature].equals("")) {
                continue;
            }
            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            double value = Double.parseDouble(lineInfo[extraFeature]);
            String extraStrata = "0";
            if (value > extraValue) {
                extraStrata = "1";
            }

            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex, extraStrata);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }


    /**
     * dump PM2.5 components for running R package.
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat dumpCleanDS_PM_Components(String input, int featureIndex,
                                                       String componentFile, String outputFile) throws IOException {
        System.out.println("starting to dump: " + featureIndex);
        // load the zipcode map information first.
        Map<String, Map<String, Double>> filters = loadFilter(componentFile, featureIndex);
        System.out.println("#zip: " + filters.size() + " are included in components");

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        bw.write("zip,date,sex,race,age,pollutant,total,death\n");
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String zip = lineInfo[0]; // also zipcode
            String date = lineInfo[1];

            // filter by values
            if (!filters.containsKey(zip)) continue;
            if (!filters.get(zip).containsKey(date)) continue;

            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, filters.get(zip).get(date));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }
            bw.write(zip + "," + date + "," + sex + "," + race + "," + age + "," +
                    filters.get(zip).get(date) + "," + label[0] + "," + label[1] + "\n");
        }

        br.close();
        bw.close();
        System.exit(0);
        return new DataFormat();
    }

    public static DataFormat loadCleanDS_PM_Components_1(String input, int feature_1, int feature_2,
                                                         String componentFile) throws IOException {
        // load the zipcode map information first.
        Map<String, Map<String, Double>> filters_1 = loadFilter(componentFile, feature_1);
        Map<String, Map<String, Double>> filters_2 = loadFilter(componentFile, feature_2);
        Set<String> zips = new HashSet<>(filters_1.keySet());
        zips.addAll(filters_2.keySet());
        System.out.println("#zip: " + zips.size() + " are included in components");

        DataFormat data = new DataFormat();
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String zip = lineInfo[0]; // also zipcode
            String date = lineInfo[1];

            // filter by values
            if (!filters_1.containsKey(zip)) continue;
            if (!filters_1.get(zip).containsKey(date)) continue;
            if (!filters_2.containsKey(zip)) continue;
            if (!filters_2.get(zip).containsKey(date)) continue;

            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, filters_1.get(zip).get(date));
            vector.set(1, filters_2.get(zip).get(date));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(zip, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * using component as a filter and run PM2.5 alone.
     * @param input
     * @param pm_index
     * @param featureIndex
     * @param componentFile
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDS_PM_Components_alone(String input, int pm_index, int featureIndex,
                                                             String componentFile) throws IOException {
        // load the zipcode map information first.
        Map<String, Map<String, Double>> filters = loadFilter(componentFile, featureIndex);
        System.out.println("#zip: " + filters.size() + " are included in components");

        DataFormat data = new DataFormat();
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String zip = lineInfo[0]; // also zipcode
            String date = lineInfo[1];

            // filter by values
            if (!filters.containsKey(zip)) continue;
            if (!filters.get(zip).containsKey(date)) continue;

            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[pm_index]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(zip, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDS_PM_Components(String input, int pm_index, int featureIndex,
                                                       String componentFile) throws IOException {
        // load the zipcode map information first.
        Map<String, Map<String, Double>> filters = loadFilter(componentFile, featureIndex);
        System.out.println("#zip: " + filters.size() + " are included in components");

        DataFormat data = new DataFormat();
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String zip = lineInfo[0]; // also zipcode
            String date = lineInfo[1];

            // filter by values
            if (!filters.containsKey(zip)) continue;
            if (!filters.get(zip).containsKey(date)) continue;

            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[pm_index]));
            vector.set(1, filters.get(zip).get(date));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(zip, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * run experiments for PM2.5 components
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDS_PM_Components(String input, int featureIndex,
                                                       String componentFile) throws IOException {
        // load the zipcode map information first.
        Map<String, Map<String, Double>> filters = loadFilter(componentFile, featureIndex);
        System.out.println("#zip: " + filters.size() + " are included in components");

        DataFormat data = new DataFormat();
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String zip = lineInfo[0]; // also zipcode
            String date = lineInfo[1];

            // filter by values
            if (!filters.containsKey(zip)) continue;
            if (!filters.get(zip).containsKey(date)) continue;

            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, filters.get(zip).get(date));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(zip, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadCleanDSStrataGrossFeatureUnNormZip(String input, int featureIndex, int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, Double>> gross = loadGrossFeatureUnNormZip("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue); // ziplevel
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESStateLargerAgeStrata(String input, int featureIndex,
                                                                      int filterIndex, double filterValue,
                                                                      int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // filter
            double fValue = Double.parseDouble(lineInfo[filterIndex]);
            if (fValue <= filterValue) { // if it is larger than given filter value continue
                continue;
            }

            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[1]); // state level
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
//            MultiKey key = new MultiKey(age);
//            key.setRace(race);
//            key.setSex(sex);
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataGrossFeatureUnNormLargerAgeStrata(String input, int featureIndex,
                                                                                int filterIndex, double filterValue,
                                                                                int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // filter
            double fValue = Double.parseDouble(lineInfo[filterIndex]);
            if (fValue <= filterValue) { // if it is larger than given filter value continue
                continue;
            }

            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
//            if (year > 2008) { // ignoring the year after 2008.
//                continue;
//            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[0]); // ziplevel
            vector.set(2, grossValue[1]); // state level
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
//            MultiKey key = new MultiKey(age);
//            key.setRace(race);
//            key.setSex(sex);
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESStateLEQAgeStrata(String input, int featureIndex,
                                                                   int filterIndex, double filterValue,
                                                                   int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // filter
            double fValue = Double.parseDouble(lineInfo[filterIndex]);
            if (fValue > filterValue) { // if it is larger than given filter value continue
                continue;
            }

            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[1]); // state level

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
//            MultiKey key = new MultiKey(age);
//            key.setRace(race);
//            key.setSex(sex);
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
    // less than filter value
    public static DataFormat loadCleanDSStrataGrossFeatureUnNormLEQAgeStrata(String input, int featureIndex,
                                                                             int filterIndex, double filterValue,
                                                                             int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // filter
            double fValue = Double.parseDouble(lineInfo[filterIndex]);
            if (fValue > filterValue) { // if it is larger than given filter value continue
                continue;
            }

            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
//            if (year > 2008) { // ignoring the year after 2008.
//                continue;
//            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[0]); // ziplevel
            vector.set(2, grossValue[1]); // state level
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
//            MultiKey key = new MultiKey(age);
//            key.setRace(race);
//            key.setSex(sex);
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadCleanDSStrataSESState(String input, int featureIndex,
                                                       int filterIndex, String filterValue,
                                                       boolean ifEqual, int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // filter
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }

            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[1]); // state level

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
//            MultiKey key = new MultiKey(age);
//            key.setRace(race);
//            key.setSex(sex);
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataGrossFeatureUnNorm(String input, int featureIndex,
                                                                 int filterIndex, String filterValue,
                                                                 boolean ifEqual, int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // filter
            if (ifEqual) {
                if (!lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            } else {
                if (lineInfo[filterIndex].equals(filterValue)) {
                    continue;
                }
            }

            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[0]); // ziplevel
            vector.set(2, grossValue[1]); // state level
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
//            MultiKey key = new MultiKey(age);
//            key.setRace(race);
//            key.setSex(sex);
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESState(String input, int featureIndex, int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[1]); // state level

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataGrossFeatureUnNorm(String input, int featureIndex, int strataFeature,
                                                                 String filter) throws IOException {
        System.out.println("load the monitor site.");
        Set<String> filterZip = loadFilterMonitor(filter);
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!filterZip.contains(lineInfo[0])) {
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[0]); // ziplevel
            vector.set(2, grossValue[1]); // state level
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataGrossFeatureUnNorm(String input, int featureIndex, int strataFeature,
                                                                 String meanFile, double meanValue) throws IOException {
        Set<String> lowZip = loadLowZip(meanFile, meanValue);
        System.out.println("the number of zipCode which is lower than " + meanValue + " is " + lowZip.size());
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);

            String zipcode = lineInfo[0];
            if (!lowZip.contains(zipcode)) {
                continue;
            }
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[0]); // ziplevel
            vector.set(2, grossValue[1]); // state level
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataGrossFeatureUnNormRange(String input, int featureIndex, int strataFeature, int min, int max) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            double pmValue = Double.parseDouble(lineInfo[featureIndex]);
            if (pmValue < min || pmValue > max) {
                continue;
            }
            vector.set(0, pmValue);
            vector.set(1, grossValue[0]); // ziplevel
            vector.set(2, grossValue[1]); // state level
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataGrossFeatureUnNorm(String input, int featureIndex, int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[0]); // ziplevel
            vector.set(2, grossValue[1]); // state level
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataGrossFeature(String input, int featureIndex, int strataFeature) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue[0]); // ziplevel
            vector.set(2, grossValue[1]); // state level
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESFeature(String input, int featureIndex1, int featureIndex2, double beta, double bias, int[] stratas,
                                                         double[] levelValues, int level) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double grossValue = gross.get(year).get(lineInfo[0])[0];

            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex1].equals("NA") ||
                    lineInfo[featureIndex2].equals("") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            double featureValue = pm - beta * no2 - bias;
            vector.setQuick(0,featureValue);

            switch (level) {
                case 1: {// not poorest
                    if (grossValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (grossValue < levelValues[0] || grossValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (grossValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESFeature(String input, int featureIndex1, int featureIndex2, double beta, double bias, int[] stratas,
                                                         double[] levelValues, int level, int filterIndex, String filterValue) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!lineInfo[filterIndex].equals(filterValue)) {
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double grossValue = gross.get(year).get(lineInfo[0])[0];

            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex1].equals("NA") ||
                    lineInfo[featureIndex2].equals("") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            double featureValue = pm - beta * no2 - bias;
            vector.setQuick(0,featureValue);

            switch (level) {
                case 1: {// not poorest
                    if (grossValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (grossValue < levelValues[0] || grossValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (grossValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataSESFeature(String input, int featureIndex, int[] stratas,
                                                         double[] levelValues, int level, int filterIndex, String filterValue) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!lineInfo[filterIndex].equals(filterValue)) {
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double grossValue = gross.get(year).get(lineInfo[0])[0];

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(3);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);

            switch (level) {
                case 1: {// not poorest
                    if (grossValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (grossValue < levelValues[0] || grossValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (grossValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
                    }
                    break;
                }
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESFeatureUnnorm(String input, int featureIndex, int[] stratas,
                                                               double[] levelValues, int level) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double grossValue = gross.get(year).get(lineInfo[0])[0]; // zip_level value

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(3);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);

            switch (level) {
                case 1: {// not poorest
                    if (grossValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (grossValue < levelValues[0] || grossValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (grossValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
                    }
                    break;
                }
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESFeatureState(String input, int featureIndex, int[] stratas,
                                                              double[] levelValues, int level,
                                                              int filterIndex, String filterValue ) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!lineInfo[filterIndex].equals(filterValue)) {
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double[] grossValues = gross.get(year).get(lineInfo[0]);

            double grossValue = grossValues[0];

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(4);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);

            switch (level) {
                case 1: {// not poorest
                    if (grossValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (grossValue < levelValues[0] || grossValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (grossValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
            }
            vector.setQuick(3,grossValues[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(4);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESFeatureState(String input, int featureIndex, int[] stratas,
                                                              double[] levelValues, int level) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double[] grossValues = gross.get(year).get(lineInfo[0]);

            double grossValue = grossValues[0];

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(4);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);

            switch (level) {
                case 1: {// not poorest
                    if (grossValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (grossValue < levelValues[0] || grossValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (grossValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
            }
            vector.setQuick(3,grossValues[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(4);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESFeatureState_no_indicator(String input, int featureIndex, int[] stratas,
                                                                           double[] levelValues, int level) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double[] grossValues = gross.get(year).get(lineInfo[0]);

            double grossValue = grossValues[0];

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(3);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);

            switch (level) {
                case 1: {// not poorest
                    if (grossValue >= levelValues[0]) {
                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (grossValue < levelValues[0] || grossValue >= levelValues[1]) {
                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (grossValue < levelValues[1]) {
                        vector.setQuick(1, pollution);
                    }
                    break;
                }
            }
            vector.setQuick(2,grossValues[1]); // ses_state

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataSESFeature_no_indicator(String input, int featureIndex, int[] stratas,
                                                                      double[] levelValues, int level) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double[] grossValues = gross.get(year).get(lineInfo[0]);

            double grossValue = grossValues[0];

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(2);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);

            switch (level) {
                case 1: {// not poorest
                    if (grossValue >= levelValues[0]) {
                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (grossValue < levelValues[0] || grossValue >= levelValues[1]) {
                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (grossValue < levelValues[1]) {
                        vector.setQuick(1, pollution);
                    }
                    break;
                }
            }
//            vector.setQuick(2,grossValues[0]); // ses_zip
//            vector.setQuick(3,grossValues[1]); // ses_state

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataLowerTertiles(String input, int featureIndex, int[] stratas,
                                                            String meanFile, double[] levelValues, int level) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<String, Double> lowZip = loadZip(meanFile);
//        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

//        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

//            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
//                continue;
//            }

//            double[] grossValues = gross.get(year).get(lineInfo[0]);

//            double grossValue = grossValues[0];

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(3);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);
            double avgValue = lowZip.get(lineInfo[0]);
            switch (level) {
                case 1: {// not poorest
                    if (avgValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (avgValue < levelValues[0] || avgValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (avgValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
            }
            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataSESFeatureLowerTertiles(String input, int featureIndex1, int featureIndex2, double beta, double bias, int[] stratas,
                                                                      String meanFile, double[] levelValues, int level) throws IOException {
        Map<String, Double> lowZip = loadZip(meanFile);
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());
//
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double[] grossValues = gross.get(year).get(lineInfo[0]);


            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex1].equals("NA") ||
                    lineInfo[featureIndex2].equals("") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(5);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            double featureValue = pm - beta * no2 - bias;
            vector.setQuick(0,featureValue);
            double avgValue = lowZip.get(lineInfo[0]);
            switch (level) {
                case 1: {// not poorest
                    if (avgValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (avgValue < levelValues[0] || avgValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (avgValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
            }

            vector.setQuick(3,grossValues[0]);
            vector.setQuick(4,grossValues[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataSESFeatureLowerTertiles(String input, int featureIndex1, int featureIndex2, double beta, double bias, int[] stratas,
                                                                      String meanFile, double[] levelValues, int level,
                                                                      int filterIndex, String filterValue) throws IOException {
        Map<String, Double> lowZip = loadZip(meanFile);
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());
//
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!lineInfo[filterIndex].equals(filterValue)) {
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double[] grossValues = gross.get(year).get(lineInfo[0]);


            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex1].equals("NA") ||
                    lineInfo[featureIndex2].equals("") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(5);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            double featureValue = pm - beta * no2 - bias;
            vector.setQuick(0,featureValue);
            double avgValue = lowZip.get(lineInfo[0]);
            switch (level) {
                case 1: {// not poorest
                    if (avgValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (avgValue < levelValues[0] || avgValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (avgValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, featureValue);
                    }
                    break;
                }
            }

            vector.setQuick(3,grossValues[0]);
            vector.setQuick(4,grossValues[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESFeatureLowerTertiles(String input, int featureIndex, int[] stratas,
                                                                      String meanFile, double[] levelValues, int level,
                                                                      int filterIndex, String filterValue) throws IOException {
        Map<String, Double> lowZip = loadZip(meanFile);
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());
//
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!lineInfo[filterIndex].equals(filterValue)) {
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double[] grossValues = gross.get(year).get(lineInfo[0]);


            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(5);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);
            double avgValue = lowZip.get(lineInfo[0]);
            switch (level) {
                case 1: {// not poorest
                    if (avgValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (avgValue < levelValues[0] || avgValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (avgValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
            }

            vector.setQuick(3,grossValues[0]);
            vector.setQuick(4,grossValues[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataSESFeatureLowerTertiles(String input, int featureIndex, int[] stratas,
                                                                      String meanFile, double[] levelValues, int level) throws IOException {
        Map<String, Double> lowZip = loadZip(meanFile);
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());
//
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double[] grossValues = gross.get(year).get(lineInfo[0]);


            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(5);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);
            double avgValue = lowZip.get(lineInfo[0]);
            switch (level) {
                case 1: {// not poorest
                    if (avgValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (avgValue < levelValues[0] || avgValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (avgValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
            }

            vector.setQuick(3,grossValues[0]);
            vector.setQuick(4,grossValues[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadCleanDSStrataSESFeature(String input, int featureIndex, int[] stratas,
                                                         double[] levelValues, int level) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) { // no such zip code current year
                continue;
            }

            double[] grossValues = gross.get(year).get(lineInfo[0]);

            double grossValue = grossValues[0];

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }


            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            DenseVector vector = new DenseVector(5);
            double pollution =  Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0,pollution);

            switch (level) {
                case 1: {// not poorest
                    if (grossValue >= levelValues[0]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 2: { // not middle class
                    if (grossValue < levelValues[0] || grossValue >= levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
                case 3: { // not the richest class
                    if (grossValue < levelValues[1]) {
                        vector.setQuick(1, 1);
                        vector.setQuick(2, pollution);
//                        vector.setQuick(1, pollution);
                    }
                    break;
                }
            }
            vector.setQuick(3,grossValues[0]);
            vector.setQuick(4,grossValues[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataGrossFiliter(String input, int featureIndex, int strataFeature,
                                                           int level, double[] levelValues) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, Double>> gross = loadGross("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv", level, levelValues);

        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
//            double grossValue = gross.get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
//            vector.set(1, grossValue);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
//    public static DataFormat loadCleanDSStrataGrossFiliter(String input, int featureIndex, int strataFeature,
//                                                           double filter, boolean isLow) throws IOException {
//        System.out.println("load from gross_income_by_state");
//        Map<String, Double> oldgross = loadGross("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.tsv", level, levelValues);
//
//        Map<String, Double> gross = new HashMap<>();
//
//        if (isLow) {
//            for (Map.Entry<String, Double> item : oldgross.entrySet()) {
//                if (item.getValue() <= filter) {
//                    gross.put(item.getKey(), item.getValue());
////                    System.out.println(item.getKey() + ": " + item.getValue());
//                }
//            }
//        } else {
//            for (Map.Entry<String, Double> item : oldgross.entrySet()) {
//                if (item.getValue() >= filter) {
//                    gross.put(item.getKey(), item.getValue());
//                }
//            }
//        }
//
//
//        System.out.println("Total number of zipcodes after Gross File: " + gross.size());
//
//        DataFormat data = new DataFormat();
//
//        BufferedReader br = new BufferedReader(new FileReader(input));
//        String line;
//        int lineCount = -1;
//        while ((line = br.readLine()) != null) {
//            lineCount += 1;
//            if (lineCount < 1) {
//                continue;
//            }
//            String[] lineInfo = line.split(",", -1);
//            if (!gross.containsKey(lineInfo[0])) {
//                continue;
//            }
////            double grossValue = gross.get(lineInfo[0]);
//
//            // none exist for value
//            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
//                continue;
//            }
//
//            // parse key
////            String date = lineInfo[1];
//            String strata = lineInfo[strataFeature];
//            String sex = lineInfo[2];
//            String race = lineInfo[3];
//            String age = lineInfo[4];
//            int ageInt = Integer.parseInt(age);
//            if (ageInt >= 90){
//                age = "90";
//            }
//            // set feature value
//            DenseVector vector = new DenseVector(1);
//            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
////            vector.set(1, grossValue);
//
//            // parse label: life and death
//            int[] label = new int[2];
//            label[0] = Integer.parseInt(lineInfo[5]);
//            label[1] = Integer.parseInt(lineInfo[6]);
//            if (label[0] == 0) {
//                continue;
//            }
//
//            // insert row data
//            MultiKey key = new MultiKey(strata, age, race, sex);
//            Pair<int[], Vector> pair = new Pair<>(label, vector);
//            data.insertRowData(key, pair);
//        }
//
//        br.close();
//        data.setNumFeatures(1);
//        data.setNumStrata(data.getData().size());
//
//        return data;
//    }

    public static void dumpFeatures(String input, int featureIndex1, int featureIndex2) throws IOException {
        System.out.println("load from gross_income_by_state for all years");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes from Gross File: " + gross.size());

        BufferedWriter bw = new BufferedWriter(new FileWriter("/scratch/wang.bin/health/DSenrollee/final_1/gross_features_2000_2008.tsv"));
        bw.write("year\tzipcode\tpm\tno2\tzip_gross\tstate_gross\n");


        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex1].equals("") || lineInfo[featureIndex1].equals("NA")) {
                continue;
            }
            if (lineInfo[featureIndex2].equals("") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }
            String zipcode = lineInfo[0];
            bw.write(year + "\t" +zipcode + "\t" + lineInfo[featureIndex1] + "\t" + lineInfo[featureIndex2] + "\t" + grossValue[0] + "\t" + grossValue[1] + "\n");
        }
        br.close();
        bw.close();
    }
    public static DataFormat loadCleanDSStrataGross(String input, int featureIndex, int strataFeature, int extraF1, int extraF2) throws IOException {
        System.out.println("load from gross_income_by_state");
        Map<String, Double>[] gross = loadGrossWithFeature("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.tsv", extraF1, extraF2);
        System.out.println("Total number of zipcodes from Gross File: " + gross[0].size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            if (!gross[0].containsKey(lineInfo[0])) {
                continue;
            }
            double grossValue = gross[0].get(lineInfo[0]);
            double grossValue1 = gross[1].get(lineInfo[0]);

            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            vector.set(1, grossValue);
            vector.set(2, grossValue1);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataEqualFeatureGrossFeature(String input, int featureIndex, int[] stratas,
                                                                       int notFeature, String notValue) throws IOException {
        DataFormat data = new DataFormat();
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            String nonF = lineInfo[notFeature];
            DenseVector vector = new DenseVector(5);
            double featureValue = Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0, featureValue);
            if (nonF.equals(notValue)) { // equal
                vector.setQuick(1, 1);
                vector.setQuick(2, featureValue);
            }
            vector.set(3, grossValue[0]);
            vector.set(4, grossValue[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrataNotLessFeatureGrossFeature(String input, int featureIndex, int[] stratas,
                                                                         int notFeature, double notValue) throws IOException {
        DataFormat data = new DataFormat();
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            double nonF = Double.parseDouble(lineInfo[notFeature]);
            DenseVector vector = new DenseVector(5);
            double featureValue = Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0, featureValue);
            if (nonF > notValue) {
                vector.setQuick(1, 1);
                vector.setQuick(2, featureValue);
            }
            vector.set(3, grossValue[0]);
            vector.set(4, grossValue[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataNotMoreFeatureGrossFeature(String input, int featureIndex, int[] stratas,
                                                                         int notFeature, double notValue) throws IOException {
        DataFormat data = new DataFormat();
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            double nonF = Double.parseDouble(lineInfo[notFeature]);
            DenseVector vector = new DenseVector(5);
            double featureValue = Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0, featureValue);
            if (nonF <= notValue) {
                vector.setQuick(1, 1);
                vector.setQuick(2, featureValue);
            }
            vector.set(3, grossValue[0]);
            vector.set(4, grossValue[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadCleanDSResidualWithSESUnNormEqual(String input, int featureIndex1, int featureIndex2, double beta, double bias,
                                                                    int filterIndex, String filterValue, boolean ifEqual,
                                                                    int[] stratas, int notFeature, String notValue) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (filterIndex > -1) { // only filterIndex is larger than -1, it filters
                if (ifEqual) {
                    if (!lineInfo[filterIndex].equals(filterValue)) {
                        continue;
                    }
                } else {
                    if (lineInfo[filterIndex].equals(filterValue)) {
                        continue;
                    }
                }
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]
            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            String nonF = lineInfo[notFeature];
            DenseVector vector = new DenseVector(5);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            double featureValue = pm - beta * no2 - bias;
            vector.setQuick(0, featureValue);
            if (nonF.equals(notValue)) {
                vector.setQuick(1, 1);
                vector.setQuick(2, featureValue);
            }
            vector.set(3, grossValue[0]);
            vector.set(4, grossValue[1]);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidualWithSESUnNormNotLess(String input, int featureIndex1, int featureIndex2, double beta, double bias,
                                                                      int filterIndex, String filterValue, boolean ifEqual,
                                                                      int[] stratas, int notFeature, double notValue) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (filterIndex > -1) { // only filterIndex is larger than -1, it filters
                if (ifEqual) {
                    if (!lineInfo[filterIndex].equals(filterValue)) {
                        continue;
                    }
                } else {
                    if (lineInfo[filterIndex].equals(filterValue)) {
                        continue;
                    }
                }
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]
            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            double nonF = Double.parseDouble(lineInfo[notFeature]);
            DenseVector vector = new DenseVector(5);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            double featureValue = pm - beta * no2 - bias;
            vector.setQuick(0, featureValue);
            if (nonF > notValue) {
                vector.setQuick(1, 1);
                vector.setQuick(2, featureValue);
            }
            vector.set(3, grossValue[0]);
            vector.set(4, grossValue[1]);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidualWithSESUnNormNotMore(String input, int featureIndex1, int featureIndex2, double beta, double bias,
                                                                      int filterIndex, String filterValue, boolean ifEqual,
                                                                      int[] stratas, int notFeature, double notValue) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (filterIndex > -1) { // only filterIndex is larger than -1, it filters
                if (ifEqual) {
                    if (!lineInfo[filterIndex].equals(filterValue)) {
                        continue;
                    }
                } else {
                    if (lineInfo[filterIndex].equals(filterValue)) {
                        continue;
                    }
                }
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]
            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            double nonF = Double.parseDouble(lineInfo[notFeature]);
            DenseVector vector = new DenseVector(5);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            double featureValue = pm - beta * no2 - bias;
            vector.setQuick(0, featureValue);
            if (nonF <= notValue) {
                vector.setQuick(1, 1);
                vector.setQuick(2, featureValue);
            }
            vector.set(3, grossValue[0]);
            vector.set(4, grossValue[1]);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadCleanDSResidualWithSESUnNorm(String input, int featureIndex1, int featureIndex2, double beta, double bias,
                                                               int filterIndex, String filterValue, boolean ifEqual,
                                                               int[] stratas, int notFeature, String notValue) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (filterIndex > -1) { // only filterIndex is larger than -1, it filters
                if (ifEqual) {
                    if (!lineInfo[filterIndex].equals(filterValue)) {
                        continue;
                    }
                } else {
                    if (lineInfo[filterIndex].equals(filterValue)) {
                        continue;
                    }
                }
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]
            // none exist for value
            if (lineInfo[featureIndex1].equals("")  || lineInfo[featureIndex2].equals("") ||
                    lineInfo[featureIndex1].equals("NA") || lineInfo[featureIndex2].equals("NA")) {
                continue;
            }

            // parse key
            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            // set feature value
            String nonF = lineInfo[notFeature];
            DenseVector vector = new DenseVector(5);
            double pm = Double.parseDouble(lineInfo[featureIndex1]);
            double no2 = Double.parseDouble(lineInfo[featureIndex2]);
            double featureValue = pm - beta * no2 - bias;
            vector.setQuick(0, featureValue);
            if (!nonF.equals(notValue)) {
                vector.setQuick(1, 1);
                vector.setQuick(2, featureValue);
            }
            vector.set(3, grossValue[0]);
            vector.set(4, grossValue[1]);


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static Map<String, Integer> loadO3States(String input, int stateIndex) throws IOException {
        Map<String, Integer> states = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            String state = lineInfo[stateIndex];
            states.putIfAbsent(state, states.size());
        }
        return states;
    }


    public static DataFormat loadO3CMS_ASR2008_NoState_SES(String input, int[] features, int[] stratas, int death, int life) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        int numFeature = features.length;
        // model for effect
        DataFormat data = new DataFormat();
        System.out.println("load O3CMS_ASR2008");
        System.out.println("features: " + features.toString());
        System.out.println("stratas: " + stratas.toString());

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) { // siteid does not exists
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);
            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            DenseVector vector = new DenseVector(numFeature+2);
            for (int i=0; i<features.length; i++) {
                double featureValue = Double.parseDouble(lineInfo[features[i]]);
                vector.setQuick(i, featureValue);
            }
            vector.set(numFeature, grossValue[0]); // sitelevel
            vector.set(numFeature+1, grossValue[1]); // state level

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[life]);
            label[1] = Integer.parseInt(lineInfo[death]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(numFeature+2);
        data.setNumStrata(data.getData().size());
        return data;
    }

    public static DataFormat loadO3CMS_ASR2008_NoState_SES(String input, int[] features, int[] stratas, int death) throws IOException {
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state_siteid.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());

        int numFeature = features.length;
        // model for effect
        DataFormat data = new DataFormat();
        System.out.println("load O3CMS_ASR2008");
        System.out.println("features: " + features.toString());
        System.out.println("stratas: " + stratas.toString());

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[2]);
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) { // siteid does not exists
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);
            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                key.addKey(strata);
            }

            DenseVector vector = new DenseVector(numFeature+2);
            for (int i=0; i<features.length; i++) {
                double featureValue = Double.parseDouble(lineInfo[features[i]]);
                vector.setQuick(i, featureValue);
            }
            vector.set(numFeature, grossValue[0]); // sitelevel
            vector.set(numFeature+1, grossValue[1]); // state level

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[death]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(numFeature+2);
        data.setNumStrata(data.getData().size());
        return data;
    }

    public static DataFormat loadO3CMS_ASR2008_NoState(String input, int[] features, int[] stratas, int death, int life) throws IOException {
        int numFeature = features.length;
        // model for effect
        DataFormat data = new DataFormat();
        System.out.println("load O3CMS_ASR2008");
        System.out.println("features: " + features.toString());
        System.out.println("stratas: " + stratas.toString());

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);

            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];

                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            DenseVector vector = new DenseVector(numFeature);
            for (int i=0; i<features.length; i++) {
                double featureValue = Double.parseDouble(lineInfo[features[i]]);
                vector.setQuick(i, featureValue);
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[life]);
            label[1] = Integer.parseInt(lineInfo[death]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(numFeature);
        data.setNumStrata(data.getData().size());
        return data;
    }
    public static DataFormat loadO3CMS_ASR2008_NoState(String input, int[] features, int[] stratas, int death) throws IOException {
        int numFeature = features.length;
        // model for effect
        DataFormat data = new DataFormat();
        System.out.println("load O3CMS_ASR2008");
        System.out.println("features: " + Arrays.toString(features));
        System.out.println("stratas: " + Arrays.toString(stratas));

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);

            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                key.addKey(strata);
            }

            DenseVector vector = new DenseVector(numFeature);
            for (int i=0; i<features.length; i++) {
                double featureValue = Double.parseDouble(lineInfo[features[i]]);
                vector.setQuick(i, featureValue);
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[death]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(numFeature);
        data.setNumStrata(data.getData().size());
        return data;
    }
    public static DataFormat loadO3CMS_ASR2008(String input, int[] features, int[] stratas, int death, int life) throws IOException {
        int numFeature = features.length;
        // model for effect
        DataFormat data = new DataFormat();
        System.out.println("load O3CMS_ASR2008");
        System.out.println("features: " + Arrays.toString(features));
        System.out.println("stratas: " +  Arrays.toString(stratas));
        Map<String, Integer> mapState = loadO3States(input, 1);
        System.out.println("#states: " + mapState.size());
        System.out.println("#states: " + mapState.toString());

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            if (lineInfo[life].length() < 1 || lineInfo[death].length() < 1) {
                continue;
            }
            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                key.addKey(strata);
            }

            DenseVector vector = new DenseVector(mapState.size() + numFeature);
            boolean ifSkip = false;
            for (int i=0; i<features.length; i++) {
                if (lineInfo[features[i]].length() < 1) {
                    ifSkip = true;
                    break;
                }
                double featureValue = Double.parseDouble(lineInfo[features[i]]);
                vector.setQuick(i, featureValue);
            }
            if (ifSkip) {
                continue;
            }

            String state = lineInfo[1]; // state index = 1
            vector.setQuick(mapState.get(state)+numFeature, 1.0);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[life]);
            label[1] = Integer.parseInt(lineInfo[death]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(numFeature+mapState.size());
        data.setNumStrata(data.getData().size());
        return data;
    }
    public static DataFormat loadO3CMS_ASR2008(String input, int[] features, int[] stratas) throws IOException {
        int numFeature = features.length;
        // model for effect
        DataFormat data = new DataFormat();
        System.out.println("load O3CMS_ASR2008");
        System.out.println("features: " + Arrays.toString(features));
        System.out.println("stratas: " +  Arrays.toString(stratas));
        Map<String, Integer> mapState = loadO3States(input, 1);
        System.out.println("#states: " + mapState.size());

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);

            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                key.addKey(strata);
            }

            DenseVector vector = new DenseVector(mapState.size() + numFeature);
            for (int i=0; i<features.length; i++) {
                double featureValue = Double.parseDouble(lineInfo[features[i]]);
                vector.setQuick(i, featureValue);
            }

            String state = lineInfo[1]; // state index = 1
            vector.setQuick(mapState.get(state)+numFeature, 1.0);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[16]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(numFeature+mapState.size());
        data.setNumStrata(data.getData().size());
        return data;
    }
    public static DataFormat loadCleanDSStrataNotFeatureGrossFeature(String input, int featureIndex, int[] stratas,
                                                                     int notFeature, String notValue) throws IOException {
        // model for effect
        DataFormat data = new DataFormat();
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);

            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                if (strataIndex == 4) { // age..
                    int ageInt = Integer.parseInt(strata);
                    if (ageInt >= 90) {
                        strata = "90";
                    }
                }
                key.addKey(strata);
            }

            String nonF = lineInfo[notFeature];
            DenseVector vector = new DenseVector(5);
            double featureValue = Double.parseDouble(lineInfo[featureIndex]);
            vector.setQuick(0, featureValue);
            if (!nonF.equals(notValue)) {
                vector.setQuick(1, 1);
                vector.setQuick(2, featureValue);
            }
            vector.set(3, grossValue[0]);
            vector.set(4, grossValue[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadCleanDSStrataBlackFeatureGrossFeature(String input, int featureIndex, int strataFeature) throws IOException {
        DataFormat data = new DataFormat();
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);


            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            String race = lineInfo[3];
            // 0:unknown; 2:black; 3:other; 4:asian; 5:Hispanic; 6:North American Native
            // model 2) mortality ~ b0 + b1*pollution + b2*non-black + b12 * SES_zip + b13*SES_state.
            DenseVector vector = new DenseVector(4);
            double featureValue = Double.parseDouble(lineInfo[featureIndex]);
            vector.set(0, featureValue);
            if (race != "2") { // non-black
                vector.setQuick(1, 1);
            }
            vector.set(2, grossValue[0]);
            vector.set(3, grossValue[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata);
            key.setAge(age);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(4);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataRaceFeatureGrossFeature(String input, int featureIndex, int strataFeature) throws IOException {
        DataFormat data = new DataFormat();
        System.out.println("load from gross_income_by_state for all years as features");
        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }

            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);


            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            String race = lineInfo[3];
            // 0:unknown; 2:black; 3:other; 4:asian; 5:Hispanic; 6:North American Native
            DenseVector vector = new DenseVector(13);
            double featureValue = Double.parseDouble(lineInfo[featureIndex]);
            vector.set(0, featureValue);
            switch (race) {
                case "2":
                    vector.setQuick(1,1);
                    vector.setQuick(2,featureValue);
                    break;
                case "3":
                    vector.setQuick(3, 1);
                    vector.setQuick(4, featureValue);
                    break;
                case "4":
                    vector.setQuick(5, 1);
                    vector.setQuick(6, featureValue);
                    break;
                case "5":
                    vector.setQuick(7, 1);
                    vector.setQuick(8, featureValue);
                    break;
                case "6":
                    vector.setQuick(9, 1);
                    vector.setQuick(10, featureValue);
                    break;
            }
            vector.set(11, grossValue[0]);
            vector.set(12, grossValue[1]);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata);
            key.setAge(age);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(13);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSStrataRaceFeature(String input, int featureIndex, int strataFeature) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            if (Integer.valueOf(lineInfo[1]) >= 1309) {
                continue;
            }
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            String race = lineInfo[3];
            // 0:unknown; 2:black; 3:other; 4:asian; 5:Hispanic; 6:North American Native
            DenseVector vector = new DenseVector(11);
            double featureValue = Double.parseDouble(lineInfo[featureIndex]);
            vector.set(0, featureValue);
            switch (race) {
                case "2":
                    vector.setQuick(1,1);
                    vector.setQuick(2,featureValue);
                    break;
                case "3":
                    vector.setQuick(3, 1);
                    vector.setQuick(4, featureValue);
                    break;
                case "4":
                    vector.setQuick(5, 1);
                    vector.setQuick(6, featureValue);
                    break;
                case "5":
                    vector.setQuick(7, 1);
                    vector.setQuick(8, featureValue);
                    break;
                case "6":
                    vector.setQuick(9, 1);
                    vector.setQuick(10, featureValue);
                    break;
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata);
            key.setAge(age);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(11);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSStrata(String input, int featureIndex, int strataFeature) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            if (Integer.valueOf(lineInfo[1]) >= 1309) {
                   continue;
            }
            String strata = lineInfo[strataFeature];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(strata, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }


    /**
     * load cleaned DS data, and use the given featureIndex value as the exposure and also use
     * areas(west, northeast,midwest and south as extra features.)
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDSArea(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }

            // parse key
//            String date = lineInfo[1];
//            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(5);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));
            String area = lineInfo[12];
            if (area.equals("")) {
                continue;
            }
            switch (area) {
                case "0": vector.setQuick(1, 1);
                break;
                case "1": vector.setQuick(2, 1);
                break;
                case "2": vector.setQuick(3, 1);
                break;
                case "3": vector.setQuick(4,1);
                break;
            }


            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(age);
            key.setRace(race);
            key.setSex(sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSFilterByLowerValueZip (String input, int featureIndex, String meanFile, int meanValue) throws IOException {
        Set<String> lowZip = loadLowZip(meanFile, meanValue);
        System.out.println("the number of zipCode which is lower than " + meanValue + " is " + lowZip.size());
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("") || lineInfo[featureIndex].equals("NA")) {
                continue;
            }

            // parse key
            // date before 2009,Jan: 2000 - 2008
            if (Integer.valueOf(lineInfo[1]) >= 1309) {
                continue;
            }
            // parse key
            String zipcode = lineInfo[0];
            if (!lowZip.contains(zipcode)) {
                continue;
            }
            String loc = lineInfo[0]; // zipcode
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex])); // f0: original feature

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDSFilterByLowerValueSES (String input, int featureIndex, String meanFile, int meanValue) throws IOException {
        Set<String> lowZip = loadLowZip(meanFile, meanValue);
        System.out.println("the number of zipCode which is lower than " + meanValue + " is " + lowZip.size());

        Map<Integer, Map<String, double[]>> gross = loadGrossFeatureUnNorm("/scratch/wang.bin/health/DSenrollee/final_1/gross_income_by_state.csv");
        System.out.println("Total number of zipcodes after Gross File: " + gross.size());


        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            int year = Integer.parseInt(lineInfo[1])/12 + 1900;
            if (year > 2008) { // ignoring the year after 2008.
                continue;
            }
            if (!gross.get(year).containsKey(lineInfo[0])) {
                continue;
            }
            double[] grossValue = gross.get(year).get(lineInfo[0]);// [zip_level, state_level]


            // parse key
            String zipcode = lineInfo[0];
            if (!lowZip.contains(zipcode)) {
                continue;
            }
            String loc = lineInfo[0]; // zipcode
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex])); // f0: original feature
            vector.set(1, grossValue[0]); // zipcode level
            vector.set(2, grossValue[1]); // state level

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    public static DataFormat loadCleanDSFilterByLowerValue (String input, int featureIndex, String meanFile, int meanValue) throws IOException {
        Set<String> lowZip = loadLowZip(meanFile, meanValue);
        System.out.println("the number of zipCode which is lower than " + meanValue + " is " + lowZip.size());
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            // parse key
            String zipcode = lineInfo[0];
            if (!lowZip.contains(zipcode)) {
                continue;
            }
            String loc = lineInfo[13]; // states
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex])); // f0: original feature

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadCleanDSFilter(String input, int featureIndex, String meanFile, int meanValue) throws IOException {
        Set<String> highZip = loadHighZip(meanFile, meanValue);

        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }

            // parse key
//            String date = lineInfo[1];
//            String loc = lineInfo[0];
            String loc = lineInfo[13]; // states
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex])); // f0: original feature
            if (highZip.contains(loc)) {
                vector.set(1, 1); // f1: binary feature, high=1; low=0;
                vector.set(2, Double.parseDouble(lineInfo[featureIndex])); // f2: f0*f1;
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }


    private static Map<String, Double> loadZip(String meanFile) throws IOException {
        Map<String, Double> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(meanFile));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            double value = Double.parseDouble(lineInfo[1]);
            map.put(lineInfo[0], value);
        }
        br.close();
        return map;
    }

    private static Set<String> loadLowZip(String meanFile, double meanValue) throws IOException {
        Set<String> set = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(meanFile));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            double value = Double.parseDouble(lineInfo[1]);
            if (value <= meanValue) {
                set.add(lineInfo[0]);
            }
        }
        br.close();
        return set;
    }
    private static Set<String> loadHighZip(String meanFile, double meanValue) throws IOException {
        Set<String> set = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(meanFile));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            double value = Double.parseDouble(lineInfo[1]);
            if (value >= meanValue) {
                set.add(lineInfo[0]);
            }
        }
        br.close();
        return set;
    }

    private static Map<String, Integer> loadUSDA(String usdaFile) throws IOException {
        Map<String, Integer> result = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(usdaFile));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) continue;
            String[] lineInfo = line.split(",", -1);
            int value = Integer.parseInt(lineInfo[1]);
            result.put(lineInfo[0], value);
        }
        return result;
    }


    private static Map<String, Double>[] loadGrossWithFeature(String fileName, int F1, int F2) throws IOException {
        Map<String, Double> result = new HashMap<>();
        Map<String, Double> result1 = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split("\t", -1);
            double value1 = Double.parseDouble(lineInfo[F1]);
            double value2 = Double.parseDouble(lineInfo[F2]);
            result.put(lineInfo[0], value1);
            result1.put(lineInfo[0], value2);
        }
        return new Map[] {result, result1};
    }

    private static Map<Integer, Map<String, Double>> loadGrossFeatureUnNormZip(String fileName) throws IOException {
        System.out.println("Load Gross data as features");
        Map<Integer, Map<String, Double>> res = new HashMap<>();
        // 00 to 08
        for (int i=2000; i<2009; i++) {
            res.put(i, new HashMap<>());
        }

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split("\t", -1);
            int year = Integer.parseInt(lineInfo[0]);
            Double ziplevel = Double.parseDouble(lineInfo[4]); // unnormalize;
            res.get(year).put(lineInfo[1], ziplevel);
        }
        return res;
    }
    private static Map<Integer, Map<String, double[]>> loadGrossFeatureUnNorm(String fileName) throws IOException {
        System.out.println("Load Gross data as features");
        Map<Integer, Map<String, double[]>> res = new HashMap<>();
        // 00 to 12
        for (int i=2000; i<2014; i++) {
            res.put(i, new HashMap<>());
        }

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split("\t", -1);
            int year = Integer.parseInt(lineInfo[0]);
            double ziplevel = Double.parseDouble(lineInfo[4]); // unnormalized
            double statelevel = Double.parseDouble(lineInfo[3]);
            res.get(year).put(lineInfo[1], new double[]{ziplevel, statelevel});
        }
        return res;
    }
    private static Map<Integer, Map<String, double[]>> loadGrossFeature(String fileName) throws IOException {
        System.out.println("Load Gross data as features");
        Map<Integer, Map<String, double[]>> res = new HashMap<>();
        // 00 to 08
        for (int i=2000; i<2009; i++) {
            res.put(i, new HashMap<>());
        }

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split("\t", -1);
            int year = Integer.parseInt(lineInfo[0]);
            double ziplevel = Double.parseDouble(lineInfo[2]);
            double statelevel = Double.parseDouble(lineInfo[3]);
            res.get(year).put(lineInfo[1], new double[]{ziplevel, statelevel});
        }
        return res;
    }
    private static Map<Integer, Map<String, Double>> loadGross(String fileName, int level, double[] levelValues) throws IOException {
        System.out.println("Load Gross data for level " + level);
        Map<Integer, Map<String, Double>> res = new HashMap<>();
        // 00 to 08
        for (int i=2000; i<2009; i++) {
            res.put(i, new HashMap<>());
        }

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split("\t", -1);
            int year = Integer.parseInt(lineInfo[0]);
            double value = Double.parseDouble(lineInfo[2]);
            if (level == 0 && value <= levelValues[0]) { // smaller than levelValues[0]
                res.get(year).put(lineInfo[1], value);
            } else if (level == 1 && value > levelValues[0] && value <= levelValues[1]) {
                res.get(year).put(lineInfo[1], value);
            } else if (level == 2 && value > levelValues[1]) {
                res.get(year).put(lineInfo[1], value);
            }
        }
        return res;
    }

    /**
     * load cleaned DS data, and use the given featureIndex value as the exposure.
     * @param input
     * @param featureIndex
     * @return
     * @throws IOException
     */
    public static DataFormat loadCleanDS(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }

            // parse key
//            String date = lineInfo[1];
            String loc = lineInfo[0];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(loc, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
}
