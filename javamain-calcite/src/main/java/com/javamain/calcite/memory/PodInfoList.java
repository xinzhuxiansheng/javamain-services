package com.javamain.calcite.memory;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class PodInfoList {
  private List<PodInfo> podInfos;

  public PodInfoList(List<PodInfo> podInfos) {
    this.podInfos = podInfos;
  }

  public void setPodInfoist(List<PodInfo> podInfos) {
    this.podInfos = podInfos;
  }

  public static PodInfoList create() {
    return new PodInfoList(Lists.newArrayList());
  }

  public void addItem(PodInfo item) {
    podInfos.add(item);
  }

  public Iterator<PodInfo> getIterator() {
    return podInfos.iterator();

  }
}
