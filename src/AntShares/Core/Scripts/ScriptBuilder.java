package AntShares.Core.Scripts;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

import AntShares.UIntBase;

/**
 *  脚本生成器
 */
public class ScriptBuilder // TODO : IDisposable
{
    private ByteArrayOutputStream ms = new ByteArrayOutputStream();

    /**
     *  添加操作符
     *  <param name="op">操作符</param>
     *  <returns>返回添加操作符之后的脚本生成器</returns>
     */
    public ScriptBuilder Add(ScriptOp op)
    {
        ms.write(op.getByte());
        return this;
    }

    public ScriptBuilder Add(byte op)
    {
        ms.write(op);
        return this;
    }

    /**
     *  添加一段脚本
     *  <param name="script">脚本</param>
     *  <returns>返回添加脚本之后的脚本生成器</returns>
     */
    public ScriptBuilder Add(byte[] script)
    {
        ms.write(script, 0, script.length);
        return this;
    }

    public void Dispose()
    {
        //ms.Dispose();
    }

    /**
     *  添加一段脚本，该脚本的作用是将一个整数压入栈中
     *  <param name="number">要压入栈中的整数</param>
     *  <returns>返回添加脚本之后的脚本生成器</returns>
     */
    public ScriptBuilder Push(BigInteger number)
    {
        int n = 0;
        try {
            n = number.intValueExact();
            if (n == -1) return Add(ScriptOp.OP_1NEGATE);
            if (n == 0) return Add(ScriptOp.OP_0);
            if (n > 0 && n <= 16) return Add((byte)(ScriptOp.OP_1.getByte() - 1 + n));
        } catch (ArithmeticException ae) {
            // do nothing.
        }
        return Push(number.toByteArray());
    }

    /**
     *  添加一段脚本，该脚本的作用是将一个字节数组压入栈中
     *  <param name="data">要压入栈中的字节数组</param>
     *  <returns>返回添加脚本之后的脚本生成器</returns>
     */
    public ScriptBuilder Push(byte[] data)
    {
        if (data == null)
            throw new NullPointerException();
        if (data.length <= (int)ScriptOp.OP_PUSHBYTES75.getByte())
        {
            ms.write((byte)data.length);
            ms.write(data, 0, data.length);
        }
        else if (data.length < 0x100)
        {
            Add(ScriptOp.OP_PUSHDATA1);
            ms.write((byte)data.length);
            ms.write(data, 0, data.length);
        }
        else if (data.length < 0x10000)
        {
            Add(ScriptOp.OP_PUSHDATA2);
            // TODO
//            ms.write(BitConverter.GetBytes((ushort)data.Length), 0, 2);
//            ms.Write(data, 0, data.Length);
        }
        // TODO
//        else if (data.LongLength < 0x100000000L)
//        {
//            Add(ScriptOp.OP_PUSHDATA4);
//            ms.Write(BitConverter.GetBytes((uint)data.Length), 0, 4);
//            ms.Write(data, 0, data.Length);
//        }
        else
        {
            throw new IllegalArgumentException();
        }
        return this;
    }

    /**
     *  添加一段脚本，该脚本的作用是将一个散列值压入栈中
     *  <param name="hash">要压入栈中的散列值</param>
     *  <returns>返回添加脚本之后的脚本生成器</returns>
     */
    public ScriptBuilder Push(UIntBase hash)
    {
        return Push(hash.toArray());
    }

    /**
     *  获取脚本生成器中包含的脚本代码
     */
    public byte[] ToArray()
    {
        return ms.toByteArray();
    }
}
