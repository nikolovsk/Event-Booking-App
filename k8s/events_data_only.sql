--
-- PostgreSQL database dump
--

\restrict wU2uuS3KPmGsKp5CXhLVq2wNta74nck5KEhZF79m6eDu7CuwDbaAKsnHZ6La15f

-- Dumped from database version 17.6 (Debian 17.6-1.pgdg13+1)
-- Dumped by pg_dump version 17.6 (Debian 17.6-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: event_location; Type: TABLE DATA; Schema: public; Owner: wp
--

COPY public.event_location (id, address, capacity, description, name) FROM stdin;
\.


--
-- Data for Name: event; Type: TABLE DATA; Schema: public; Owner: wp
--

COPY public.event (id, date, description, name, popularity_score, location_id) FROM stdin;
\.


--
-- Data for Name: shop_users; Type: TABLE DATA; Schema: public; Owner: wp
--

COPY public.shop_users (username, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, name, password, role, surname) FROM stdin;
\.


--
-- Name: event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: wp
--

SELECT pg_catalog.setval('public.event_id_seq', 1, false);


--
-- Name: event_location_id_seq; Type: SEQUENCE SET; Schema: public; Owner: wp
--

SELECT pg_catalog.setval('public.event_location_id_seq', 1, false);


--
-- PostgreSQL database dump complete
--

\unrestrict wU2uuS3KPmGsKp5CXhLVq2wNta74nck5KEhZF79m6eDu7CuwDbaAKsnHZ6La15f

