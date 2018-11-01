package com.mysafetynet.Model;

public class PageModel {
    private String message;

    private Result result;

    private String status;

    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", result = " + result + ", status = " + status + ", success = " + success + "]";
    }

    public class Result {
        private String detail;

        private String sort_desc;

        private String status;

        private String deleted_at;

        private String image;

        private String type;

        private String id;

        private String title;

        private String updated_at;

        private String created;

        private String previous_slug;

        private String created_at;

        private String slug;

        private String next_slug;

        private String post_date;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getSort_desc() {
            return sort_desc;
        }

        public void setSort_desc(String sort_desc) {
            this.sort_desc = sort_desc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getPrevious_slug() {
            return previous_slug;
        }

        public void setPrevious_slug(String previous_slug) {
            this.previous_slug = previous_slug;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getNext_slug() {
            return next_slug;
        }

        public void setNext_slug(String next_slug) {
            this.next_slug = next_slug;
        }

        public String getPost_date() {
            return post_date;
        }

        public void setPost_date(String post_date) {
            this.post_date = post_date;
        }

        @Override
        public String toString() {
            return "ClassPojo [detail = " + detail + ", sort_desc = " + sort_desc + ", status = " + status + ", deleted_at = " + deleted_at + ", image = " + image + ", type = " + type + ", id = " + id + ", title = " + title + ", updated_at = " + updated_at + ", created = " + created + ", previous_slug = " + previous_slug + ", created_at = " + created_at + ", slug = " + slug + ", next_slug = " + next_slug + ", post_date = " + post_date + "]";
        }
    }
}
