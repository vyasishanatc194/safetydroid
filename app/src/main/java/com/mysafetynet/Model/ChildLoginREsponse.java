package com.mysafetynet.Model;

public class ChildLoginREsponse {
    private String message;

    private Result result;

    private String status;

    private String success;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Result getResult ()
    {
        return result;
    }

    public void setResult (Result result)
    {
        this.result = result;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", result = "+result+", status = "+status+", success = "+success+"]";
    }

    public class Result {
        private String phone;

        private String thumb_image;

        private String school_name;

        private String device_type;

        private String state;

        private String id;

        private String first_name;

        private String username;

        private String stripe_id;

        private String token;

        private String age;

        private String gender;

        private String created_at;

        private String otp;

        private String user_image;

        private String parent_id;

        private String school_district_no;

        private String status;

        private String fire_base_token;

        private String deleted_at;

        private String image;

        private String child_age;

        private String batch_id;

        private String updated_at;

        private String email;

        private String dob;

        private String last_name;

        public String getPhone ()
        {
            return phone;
        }

        public void setPhone (String phone)
        {
            this.phone = phone;
        }

        public String getThumb_image ()
        {
            return thumb_image;
        }

        public void setThumb_image (String thumb_image)
        {
            this.thumb_image = thumb_image;
        }

        public String getSchool_name ()
        {
            return school_name;
        }

        public void setSchool_name (String school_name)
        {
            this.school_name = school_name;
        }

        public String getDevice_type ()
        {
            return device_type;
        }

        public void setDevice_type (String device_type)
        {
            this.device_type = device_type;
        }

        public String getState ()
        {
            return state;
        }

        public void setState (String state)
        {
            this.state = state;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getFirst_name ()
        {
            return first_name;
        }

        public void setFirst_name (String first_name)
        {
            this.first_name = first_name;
        }

        public String getUsername ()
        {
            return username;
        }

        public void setUsername (String username)
        {
            this.username = username;
        }

        public String getStripe_id ()
        {
            return stripe_id;
        }

        public void setStripe_id (String stripe_id)
        {
            this.stripe_id = stripe_id;
        }

        public String getToken ()
        {
            return token;
        }

        public void setToken (String token)
        {
            this.token = token;
        }

        public String getAge ()
        {
            return age;
        }

        public void setAge (String age)
        {
            this.age = age;
        }

        public String getGender ()
        {
            return gender;
        }

        public void setGender (String gender)
        {
            this.gender = gender;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getOtp ()
        {
            return otp;
        }

        public void setOtp (String otp)
        {
            this.otp = otp;
        }

        public String getUser_image ()
        {
            return user_image;
        }

        public void setUser_image (String user_image)
        {
            this.user_image = user_image;
        }

        public String getParent_id ()
        {
            return parent_id;
        }

        public void setParent_id (String parent_id)
        {
            this.parent_id = parent_id;
        }

        public String getSchool_district_no ()
        {
            return school_district_no;
        }

        public void setSchool_district_no (String school_district_no)
        {
            this.school_district_no = school_district_no;
        }

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        public String getFire_base_token ()
        {
            return fire_base_token;
        }

        public void setFire_base_token (String fire_base_token)
        {
            this.fire_base_token = fire_base_token;
        }

        public String getDeleted_at ()
        {
            return deleted_at;
        }

        public void setDeleted_at (String deleted_at)
        {
            this.deleted_at = deleted_at;
        }

        public String getImage ()
        {
            return image;
        }

        public void setImage (String image)
        {
            this.image = image;
        }

        public String getChild_age ()
        {
            return child_age;
        }

        public void setChild_age (String child_age)
        {
            this.child_age = child_age;
        }

        public String getBatch_id ()
        {
            return batch_id;
        }

        public void setBatch_id (String batch_id)
        {
            this.batch_id = batch_id;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getEmail ()
        {
            return email;
        }

        public void setEmail (String email)
        {
            this.email = email;
        }

        public String getDob ()
        {
            return dob;
        }

        public void setDob (String dob)
        {
            this.dob = dob;
        }

        public String getLast_name ()
        {
            return last_name;
        }

        public void setLast_name (String last_name)
        {
            this.last_name = last_name;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [phone = "+phone+", thumb_image = "+thumb_image+", school_name = "+school_name+", device_type = "+device_type+", state = "+state+", id = "+id+", first_name = "+first_name+", username = "+username+", stripe_id = "+stripe_id+", token = "+token+", age = "+age+", gender = "+gender+", created_at = "+created_at+", otp = "+otp+", user_image = "+user_image+", parent_id = "+parent_id+", school_district_no = "+school_district_no+", status = "+status+", fire_base_token = "+fire_base_token+", deleted_at = "+deleted_at+", image = "+image+", child_age = "+child_age+", batch_id = "+batch_id+", updated_at = "+updated_at+", email = "+email+", dob = "+dob+", last_name = "+last_name+"]";
        }
    }
}
