/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
/**
 *
 * @author User
 */
public interface Model_DAO <A> {
    public int autonumber (A object);
    public void insert (A object);
    public void update (A object);
    public void delete (A object);
    public List <A> getAll();
    public List <A> getCari (String key);

    public void delete(int id);

    public void delete(String id);
}
