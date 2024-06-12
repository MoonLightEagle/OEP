create table users
(
    id           bigserial primary key,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    email        varchar(255) not null,
    password     varchar      not null,
    role         varchar      not null,
    phone_number varchar(255) not null,
    address      varchar(255) not null,
    created_at   timestamp    not null,
    updated_at   timestamp    not null
);

create table courses
(
    id          bigserial primary key,
    name        varchar(255) not null,
    description varchar(255) not null,
    teacher_id  bigint       not null references users (id),
    created_at  timestamp    not null,
    updated_at  timestamp    not null
);

create table grades
(
    id          bigserial primary key,
    name        varchar(255) not null,
    description varchar(255) not null,
    created_at  timestamp    not null,
    updated_at  timestamp    not null
);

create table exams
(
    id         bigserial primary key,
    name       varchar(255) not null,
    course_id  bigint       not null references courses (id),
    start_time timestamp    not null,
    end_time   timestamp    not null,
    created_at timestamp    not null,
    updated_at timestamp    not null
);

create table questions
(
    id            bigserial primary key,
    exam_id       bigint       not null references exams (id),
    question_text varchar(255) not null,
    value         integer      not null,
    created_at    timestamp    not null,
    updated_at    timestamp    not null
);

create table question_variants
(
    id           bigserial primary key,
    question_id  bigint       not null references questions (id),
    variant_text varchar(255) not null,
    is_correct   boolean      not null,
    created_at   timestamp    not null,
    updated_at   timestamp    not null
);

create table exam_results
(
    id         bigserial primary key,
    exam_id    bigint    not null references exams (id),
    user_id    bigint    not null references users (id),
    result     integer   not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table question_answers
(
    id             bigserial primary key,
    question_id    bigint    not null references questions (id),
    student_id     bigint    not null references users (id),
    student_answer bigint    not null references question_variants (id),
    created_at     timestamp not null,
    updated_at     timestamp not null
);


create table course_grades
(
    id        bigserial primary key,
    course_id bigint not null references courses (id),
    grade_id  bigint not null references grades (id)
);

create table user_grades
(
    id       bigserial primary key,
    user_id  bigint not null references users (id),
    grade_id bigint not null references grades (id)
);
