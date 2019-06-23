# coding=utf-8
import json
from sqlalchemy import MetaData, Table, Column, Index, Sequence, ForeignKey, create_engine
from sqlalchemy.orm import sessionmaker, scoped_session
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Integer, BigInteger, SmallInteger, String, Text, Boolean, Binary, \
    PickleType, Date, DateTime, Time, TIMESTAMP, Enum, JSON, Float

from config.database_setting import DB_CONFIG as DB_CONFIG

CONNECTION_URL = "mysql+pymysql://%s:%s@%s:%s/%s?charset=%s" % \
                 (
                     DB_CONFIG["username"], DB_CONFIG["password"], DB_CONFIG["host"],
                     DB_CONFIG["port"], DB_CONFIG["database"], DB_CONFIG["charset"]
                 )

engine = create_engine(CONNECTION_URL, json_serializer=lambda obj: json.dumps(obj, ensure_ascii=False),
                       pool_recycle=1000, pool_size=200)
metadata = MetaData()
metadata.bind = engine
Session = scoped_session(sessionmaker(autocommit=False, autoflush=False, expire_on_commit=False, bind=engine))
Base = declarative_base()
Base.query = Session.query_property()


class OrmBase(object):
    def save(self, commit=True, refresh=False):
        session = Session()
        session.add(self)
        if commit:
            session.commit()
        if refresh:
            Session().refresh(self)
        Session.remove()

    def refresh(self):
        Session().refresh(self)


class get_session:
    def __init__(self):
        pass

    def __enter__(self):
        self.session = Session()
        return self.session

    def __exit__(self, exc_type, exc_val, exc_tb):
        if exc_tb is None:
            try:
                self.session.commit()
            except Exception as commit_exception:
                print("Commit Error: " + str(commit_exception))
        Session.remove()
        return True
