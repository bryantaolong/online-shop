-- 用户主表
create sequence "user_id_seq";

create table "sys_user"
(
    id                  bigint  default nextval('user_id_seq'::regclass) not null primary key,
    username            varchar(255)                                     not null,
    password            varchar(255)                                     not null,
    phone_number        varchar(50),
    email               varchar(255),
    status              integer default 0,
    roles               varchar(255),
    login_time          timestamp,
    login_ip            varchar(255),
    password_reset_time timestamp,
    login_fail_count    integer default 0,
    account_lock_time   timestamp,
    deleted             integer default 0,
    version             integer default 0,
    create_time         timestamp      default now() not null,
    update_time         timestamp,
    create_by           varchar(50),
    update_by           varchar(50)
);

CREATE INDEX idx_user_username ON sys_user (username);

-- 用户详情
CREATE TABLE sys_user_profile (
                                  user_id      BIGINT PRIMARY KEY REFERENCES sys_user(id),
                                  real_name    VARCHAR(50),
                                  gender       SMALLINT CHECK (gender BETWEEN 0 AND 1),
                                  birthday     DATE,
                                  avatar       VARCHAR(255),
                                  id_card      VARCHAR(18),
                                  id_card_front VARCHAR(255),
                                  id_card_back  VARCHAR(255),
                                  personal_sign VARCHAR(255),
                                  deleted             integer default 0,
                                  version             integer default 0,
                                  create_time         timestamp      default now() not null,
                                  update_time         timestamp,
                                  create_by           varchar(50),
                                  update_by           varchar(50)
);

-- 用户收货地址
CREATE TABLE ums_user_address (
                                  id            BIGSERIAL PRIMARY KEY,
                                  user_id       BIGINT NOT NULL REFERENCES sys_user(id),
                                  receiver_name VARCHAR(50)  NOT NULL,
                                  receiver_phone VARCHAR(20) NOT NULL,
                                  province      VARCHAR(50),
                                  city          VARCHAR(50),
                                  district      VARCHAR(50),
                                  detail_address VARCHAR(200),
                                  postal_code   VARCHAR(10),
                                  is_default    SMALLINT DEFAULT 0,
                                  address_tag   SMALLINT,
                                  tag_name      VARCHAR(20),
                                  deleted             integer default 0,
                                  version             integer default 0,
                                  create_time         timestamp      default now() not null,
                                  update_time         timestamp,
                                  create_by           varchar(50),
                                  update_by           varchar(50)
);

-- 商品分类
CREATE TABLE pms_category (
                              id         BIGSERIAL PRIMARY KEY,
                              name       VARCHAR(100) NOT NULL,
                              parent_id  BIGINT REFERENCES pms_category(id),
                              level      SMALLINT,
                              sort_order INTEGER,
                              deleted             integer default 0,
                              version             integer default 0,
                              create_time         timestamp      default now() not null,
                              update_time         timestamp,
                              create_by           varchar(50),
                              update_by           varchar(50)
);

-- 品牌
CREATE TABLE pms_brand (
                           id          BIGSERIAL PRIMARY KEY,
                           name        VARCHAR(100) NOT NULL,
                           logo        VARCHAR(255),
                           description TEXT,
                           deleted             integer default 0,
                           version             integer default 0,
                           create_time         timestamp      default now() not null,
                           update_time         timestamp,
                           create_by           varchar(50),
                           update_by           varchar(50)
);

-- 商品 SPU
CREATE TABLE pms_product (
                             id          BIGSERIAL PRIMARY KEY,
                             name        VARCHAR(200) NOT NULL,
                             category_id BIGINT REFERENCES pms_category(id),
                             brand_id    BIGINT REFERENCES pms_brand(id),
                             description TEXT,
                             main_image  VARCHAR(255),
                             status      SMALLINT DEFAULT 0,   -- 0-下架 1-上架
                             deleted             integer default 0,
                             version             integer default 0,
                             create_time         timestamp      default now() not null,
                             update_time         timestamp,
                             create_by           varchar(50),
                             update_by           varchar(50)
);

CREATE INDEX idx_product_name ON pms_product (name);

-- SKU
CREATE TABLE pms_sku (
                         id            BIGSERIAL PRIMARY KEY,
                         product_id    BIGINT NOT NULL REFERENCES pms_product(id),
                         sku_code      VARCHAR(50) UNIQUE,
                         sku_name      VARCHAR(100),
                         price         NUMERIC(10,2),
                         original_price NUMERIC(10,2),
                         stock         INTEGER,
                         specifications JSONB,
                         deleted             integer default 0,
                         version             integer default 0,
                         create_time         timestamp      default now() not null,
                         update_time         timestamp,
                         create_by           varchar(50),
                         update_by           varchar(50)
);

-- 商品图片
CREATE TABLE pms_product_image (
                                   id        BIGSERIAL PRIMARY KEY,
                                   product_id BIGINT NOT NULL REFERENCES pms_product(id),
                                   sku_id     BIGINT REFERENCES pms_sku(id),
                                   image_url  VARCHAR(255),
                                   sort_order INTEGER,
                                   deleted             integer default 0,
                                   version             integer default 0,
                                   create_time         timestamp      default now() not null,
                                   update_time         timestamp,
                                   create_by           varchar(50),
                                   update_by           varchar(50)
);

CREATE TABLE ums_cart_item (
                               id            BIGSERIAL PRIMARY KEY,
                               user_id       BIGINT NOT NULL REFERENCES sys_user(id),
                               product_id    BIGINT NOT NULL,
                               sku_id        BIGINT NOT NULL,
                               product_name  VARCHAR(200),
                               sku_name      VARCHAR(200),
                               product_image VARCHAR(255),
                               price         NUMERIC(10,2),
                               quantity      INTEGER,
                               specifications JSONB,
                               deleted             integer default 0,
                               version             integer default 0,
                               create_time         timestamp      default now() not null,
                               update_time         timestamp,
                               create_by           varchar(50),
                               update_by           varchar(50)
);

-- 订单主表
CREATE TABLE oms_order (
                           id            BIGSERIAL PRIMARY KEY,
                           order_no      VARCHAR(50) UNIQUE NOT NULL,
                           user_id       BIGINT NOT NULL REFERENCES sys_user(id),
                           total_amount  NUMERIC(10,2),
                           payment_amount NUMERIC(10,2),
                           freight_amount NUMERIC(10,2),
                           payment_type  SMALLINT, -- 枚举
                           status        SMALLINT, -- 枚举
                           shipping_name VARCHAR(100),
                           shipping_code VARCHAR(50),
                           receiver_name VARCHAR(50),
                           receiver_phone VARCHAR(20),
                           receiver_address VARCHAR(500),
                           note          TEXT,
                           payment_time  TIMESTAMP,
                           delivery_time TIMESTAMP,
                           receive_time  TIMESTAMP,
                           deleted             integer default 0,
                           version             integer default 0,
                           create_time         timestamp      default now() not null,
                           update_time         timestamp,
                           create_by           varchar(50),
                           update_by           varchar(50)
);

CREATE INDEX idx_order_user ON oms_order (user_id);
CREATE INDEX idx_order_no ON oms_order (order_no);

-- 订单明细
CREATE TABLE oms_order_item (
                                id            BIGSERIAL PRIMARY KEY,
                                order_id      BIGINT NOT NULL REFERENCES oms_order(id),
                                order_no      VARCHAR(50),
                                product_id    BIGINT,
                                sku_id        BIGINT,
                                product_name  VARCHAR(200),
                                sku_name      VARCHAR(200),
                                product_image VARCHAR(255),
                                product_price NUMERIC(10,2),
                                quantity      INTEGER,
                                total_price   NUMERIC(10,2),
                                specifications JSONB,
                                deleted             integer default 0,
                                version             integer default 0,
                                create_time         timestamp      default now() not null,
                                update_time         timestamp,
                                create_by           varchar(50),
                                update_by           varchar(50)
);

-- 订单历史（可选）
CREATE TABLE oms_order_history (
                                   id          BIGSERIAL PRIMARY KEY,
                                   order_id    BIGINT REFERENCES oms_order(id),
                                   order_status SMALLINT,
                                   note        VARCHAR(255),
                                   deleted             integer default 0,
                                   version             integer default 0,
                                   create_time         timestamp      default now() not null,
                                   update_time         timestamp,
                                   create_by           varchar(50),
                                   update_by           varchar(50)
);

CREATE TABLE pay_payment (
                             id               BIGSERIAL PRIMARY KEY,
                             order_id         BIGINT NOT NULL REFERENCES oms_order(id),
                             order_no         VARCHAR(50),
                             payment_type     SMALLINT,
                             transaction_id   VARCHAR(100),
                             payment_amount   NUMERIC(10,2),
                             payment_status   SMALLINT,
                             payment_time     TIMESTAMP,
                             callback_content TEXT,
                             callback_time    TIMESTAMP,
                             deleted             integer default 0,
                             version             integer default 0,
                             create_time         timestamp      default now() not null,
                             update_time         timestamp,
                             create_by           varchar(50),
                             update_by           varchar(50)
);

CREATE INDEX idx_payment_order ON pay_payment (order_no);