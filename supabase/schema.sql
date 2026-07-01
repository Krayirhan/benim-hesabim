create table if not exists transactions (
id uuid primary key,
user_id uuid not null references auth.users(id) on delete cascade,
title text not null,
amount_minor bigint not null,
type text not null check (type in ('INCOME', 'EXPENSE')),
category text not null,
transaction_date date not null,
note text,
created_at timestamptz not null default now(),
updated_at timestamptz not null default now()
);

alter table transactions enable row level security;

create policy "Users can read own transactions"
on transactions for select
using (auth.uid() = user_id);

create policy "Users can insert own transactions"
on transactions for insert
with check (auth.uid() = user_id);

create policy "Users can update own transactions"
on transactions for update
using (auth.uid() = user_id)
with check (auth.uid() = user_id);

create policy "Users can delete own transactions"
on transactions for delete
using (auth.uid() = user_id);
