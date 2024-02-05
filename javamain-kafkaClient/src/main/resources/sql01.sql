CREATE TABLE TABLE_NAME (
                            `id` STRING,
                            `name` STRING,
                            `subrecord` ROW <
                                `id` STRING,
                            `name` STRING,
                            `subrecord` ROW <
                                `id` STRING,
                            `name` STRING
                                >
                                >
)