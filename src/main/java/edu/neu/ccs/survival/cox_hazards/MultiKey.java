package cox_hazards;

import cox_hazards.MultiKey;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Rainicy on 3/13/16.
 */
public class MultiKey {
    private String date;
//    public String location;
    private String age;
    private String race;
    private String sex;
    private String region;

    public void setSingleKey(boolean singleKey) {
        this.singleKey = singleKey;
    }

    private boolean singleKey = false;

    public MultiKey(String date, String age, String race, String sex, String region) {
        this.date = date;
        this.age = age;
        this.race = race;
        this.sex = sex;
        this.region = region;
    }

    public MultiKey(String date, String age, String race, String sex) {
       this(date, age, race, sex, "");
    }


    public MultiKey(String date) {
        this.date = date;
        this.age = null;
        this.race = null;
        this.sex = null;
        this.region = null;
//        this.singleKey = true;
    }

    public MultiKey() {
        this.date = null;
        this.age = null;
        this.race = null;
        this.sex = null;
        this.region = null;
    }


    public void addKey(String key) {
        if (this.date == null) {
            this.date = key;
        } else if (this.age == null) {
            this.age = key;
        } else if (this.race == null) {
            this.race = key;
        } else if (this.sex == null) {
            this.sex = key;
        } else if (this.region == null) {
            this.region = key;
        } else {
            System.out.println("Too many keys...");
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        MultiKey multiKey = (MultiKey) obj;

//        if (date != null ? !date.equals(multiKey.date) : multiKey.date != null) return false;
//        if (age != null ? !age.equals(multiKey.age) : multiKey.age != null) return false;
//        if (race != null ? !race.equals(multiKey.race) : multiKey.race != null) return false;
//        if (sex != null ? !sex.equals(multiKey.sex) : multiKey.sex != null) return false;
//        if (region != null ? !region.equals(multiKey.region) : multiKey.region != null) return false;
//        return true;


        // new one:
        if (singleKey) {
            return new EqualsBuilder().append(date, multiKey.date).isEquals();
        }
        return new EqualsBuilder()
                .append(date, multiKey.date)
                .append(age, multiKey.age)
                .append(race, multiKey.race)
                .append(sex, multiKey.sex)
                .append(region, multiKey.region)
                .isEquals();
    }

    @Override
    public int hashCode() {
        // refer: http://stackoverflow.com/questions/2730865/how-do-i-calculate-a-good-hash-code-for-a-list-of-strings
//        final int prime = 31;
//        int result = 1;
//
//        result = result * prime + date.hashCode();
//        result = result * prime + age.hashCode();
//        result = result * prime + race.hashCode();
//        result = result * prime + sex.hashCode();
//        result = result * prime + region.hashCode();
//
//        return result;

        // refer: https://stackoverflow.com/questions/27581/what-issues-should-be-considered-when-overriding-equals-and-hashcode-in-java
        if (singleKey) {
            return new HashCodeBuilder(17, 31).append(date).hashCode();
        }
        return new HashCodeBuilder(17, 31)
                .append(date)
                .append(age)
                .append(race)
                .append(sex)
                .append(region)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "date: " + date + "\tage: " + age + "\trace: " + race + "\tsex: " + sex + "\tregion: " + region;
    }
}
