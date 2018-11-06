/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object Main {
  def main(args: Array[String]) {

    val spark = SparkSession
      .builder
      .appName("Test")
      .getOrCreate()

    import spark.implicits._

    spark.conf.set("spark.sql.shuffle.partitions", "1")
    val input1 = spark.readStream
      .format("rate")
      .option("numPartitions", "1")
      .option("rowsPerSecond", "5")
      .load()
      .as("input1")
    val input2 = spark.readStream
      .format("rate")
      .option("numPartitions", "1")
      .option("rowsPerSecond", "5")
      .load()
      .as("input2")
    val input = input1.join(input2, "value")
      .select(col("input1.timestamp"), col("input1.value").as("value"))
      .withWatermark("input1.timestamp", "1 minutes")
    val query = input.toDF().writeStream.format("noop").start()
    query.awaitTermination()
  }
}
